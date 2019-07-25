package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import parser.structure.Entity;
import parser.structure.EntityStack;
import parser.structure.ID;
import parser.structure.NativeFunction;

public class Rules extends ParserBase {
  public Sub root;
  
  public final HashMap<String, SymbolMask> masks = new HashMap<>();
  public SymbolMask getMask(String name) {
    SymbolMask mask = masks.get(name);
    if(mask == null) error("Cannot find symbol mask \"" + name + "\"");
    return mask;
  }
  
  public final HashMap<String, Sub> subs = new HashMap<>();
  public final HashMap<String, Error> errors = new HashMap<>();
  
  public Sub getSub(String name) {
    Sub sub = subs.get(name);
    if(sub == null) {
      sub = new Sub(name, null);
      subs.put(name, sub);
    }
    return sub;
  }
  
  private BufferedReader reader;
  @SuppressWarnings("ResultOfObjectAllocationIgnored")
  public Rules load(String fileName) {
    currentFileName = fileName;
    masks.clear();
    masks.put("tab", new SymbolMask('\t'));
    masks.put("space", new SymbolMask(' '));
    masks.put("newline", new SymbolMask('\n').set('\r'));
    masks.put("eof", new SymbolMask(129));
    subs.clear();
    try {
      reader = new BufferedReader(new FileReader(fileName));
      String line;
      lineNum = 0;
      while((line = reader.readLine()) != null) {
        lineNum++;
        line = line.trim();
        if(line.isEmpty() || line.startsWith("//")) continue;
        int equalPos = line.indexOf('=');
        int colonPos = line.indexOf(':');
        if(equalPos >= 0 && (equalPos < colonPos || colonPos < 0)) {
          SymbolMask mask = new SymbolMask();
          for(String symbol : line.substring(equalPos + 1).trim().split(" ")) {
            if(symbol.length() == 1) {
              mask.set(symbol.charAt(0));
            } else if(symbol.length() == 3 && symbol.charAt(1) == '-') {
              mask.set(symbol.charAt(0), symbol.charAt(2));
            } else if(symbol.length() >= 2) {
              SymbolMask mask2 = masks.get(symbol);
              if(mask2 == null) error("Mask \"" + symbol
                  + "\" is not found");
              mask.or(mask2);
            }
          }
          masks.put(line.substring(0, equalPos).trim(), mask);
        } else if(line.startsWith("ERROR ")) {
          String[] parts = line.substring(6).split(":");
          errors.put(parts[0].trim(), new Error(parts[1].trim()));
        } else {
          if(colonPos < 0 && equalPos < 0) error(": or = expected");
          String name = line.substring(0, colonPos).trim();
          Sub sub = getSub(name);
          if(sub.action != null) error("Sub \"" + name
              + "\" is already defined");
          sub.action = actionChain(line.substring(colonPos + 1), null, sub);
        }
      }
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", fileName + "Cannot read " + fileName + ".");
    }
    
    root = subs.get("root");
    return this;
  }

  private Action actionChain(String commands, Action lastAction, Sub currentSub)
      throws IOException {
    Action firstAction = null, currentAction = null;
    boolean exit = false;
    for(String command : commands.trim().split(" ")) {
      Action action;
      int bracketPos = command.indexOf('(');
      String name = bracketPos < 0 ? command : command.substring(0, bracketPos);
      String params = bracketPos < 0 ? "" :command.substring(bracketPos + 1
          , command.length() - 1);

      //System.out.println(name);
      ActionSwitch switchAction = null;
      boolean copy = false;
      switch(name) {
        case "RETURN":
          action = new ActionReturn();
          break;
        case "SKIP":
          action = new ActionSkip();
          break;
        case "SAVEPOS":
          action = new ActionSavePos();
          break;
        case "LOADPOS":
          action = new ActionLoadPos();
          break;
        case "ADD":
          action = new ActionAdd(stringParam(params));
          break;
        case "EXPECT":
          if(params.length() != 3) error("EXPECT command requires one symbol"
              + " as parameter");
          action = new ActionExpect(params.charAt(1));
          break;
        case "CLEAR":
          action = new ActionClear();
          break;
        case ">>":
          action = new ActionForward();
          break;
        case "CREATE":
          String[] param = params.split(",");
          ID id = ID.get(param[0]);
          if(id == ID.classParameterID) {
            action = new ActionCreate(null, id, null);
          } else {
            if(id == ID.moduleID) {
              action = new ActionCreate(null, id, null);
            } else {
              NativeFunction function = NativeFunction.all.get(id);
              EntityStack stack = function == null ? EntityStack.get(id)
                  : EntityStack.call;
              if(stack == EntityStack.block) {
                if(param.length != 2) error("CREATE block command requires 2"
                    + " parameters");
                action = new ActionCreate(stack, ID.get(param[1]), null);
              } else {
                if(param.length != 1) error("CREATE command requires single"
                    + " parameter");
                action = new ActionCreate(stack, null, function);
              }
            }
          }
          break;
        case "COPY":
          copy = true;
        case "MOVE":
          param = params.split(",");
          NativeFunction function = NativeFunction.all.get(ID.get(param[0]));
          if(function != null) {
            action = new ActionMoveNewFunction(function, EntityStack.get(param[1]));
          } else {
            EntityStack<Entity> stack0 = EntityStack.get(param[0]);
            if(param.length == 1) {
              action = new ActionMove(stack0, stack0, copy);
            } else {
              if(param.length != 2) error("MOVE command requires 2 parameters");
              action = new ActionMove(stack0, EntityStack.get(param[1]), copy);
            }
          }
          break;
        case "SET":
          param = params.split(",");
          if(param.length != 3) error("SET command requires 3 parameters");
          action = new ActionSet(EntityStack.get(param[0]), ID.get(param[1])
              , EntityStack.get(param[2]));
          break;
        case "USE":
          action = new ActionUse(ID.get(params));
          break;
        case "REMOVE":
          action = new ActionRemove(EntityStack.get(params));
          break;
        case "{":
          switchAction = new ActionSwitchSymbol();
        case "SWITCHID":
          if(switchAction == null) switchAction = new ActionSwitchID();
          
          String line;
          Action back = new ActionGoToAction(switchAction);
          while(true) {
            if((line = reader.readLine()) == null)
              error("Unexpected end of file");
            lineNum++;
            line = line.trim();
            if(line.isEmpty() || line.startsWith("//")) continue;
            if(line.equals("}")) break;
            int colonPos = line.indexOf(':');
            if(colonPos < 0) error(": expected");
            String token = line.substring(0, colonPos).trim();
            Action actionChain = actionChain(line.substring(colonPos + 1), back
                , currentSub);
            if(token.startsWith("\"")) {
              switchAction.setStringAction(stringParam(token), actionChain);
            } else if(token.equals("other")) {
              switchAction.setOtherAction(actionChain);
            } else if(name.equals("{")) {
              SymbolMask symbolMask = getMask(token);
              if(symbolMask == null) error("Mask \"" + token + "\" is not found");
              switchAction.setMaskAction(symbolMask, actionChain);
              //break;
            } else {
              error("Invalid token");
            }
          }
          action = switchAction;
          exit = true;
          break;
        default:
          Error error = errors.get(name);
          if(error == null) {
            //System.out.println(name);
            if(bracketPos < 0) {
              action = new ActionGoToSub(getSub(name));
            } else {
              action = new ActionSub(getSub(name), currentSub, params.isEmpty()
                  ? null : getSub(params));
            }
          } else {
            if(!params.isEmpty()) error = error.derive(
                stringParam(params).replace("_"," "));
            action = error;
          }
      }
      action.parserLine = lineNum;
      if(currentAction == null) {
        firstAction = action;
      } else {
        currentAction.nextAction = action;
      }
      currentAction = action;
      if(exit) break;
    }
    
    if(lastAction != null) currentAction.nextAction = lastAction;
    return firstAction;
  }

  private String strucString;
  private int pos;
  
  private int readNum() {
    int start = pos;
    while(true) {
      char c = strucString.charAt(pos);
      if(c < '0' || c > '9') break;
      pos++;
    }
    if(start == pos) error("Integer number expected");
    return Integer.parseInt(strucString.substring(start, pos));
  }

  private String stringParam(String str) {
    if(!str.endsWith("\"") || str.length() < 2) error("Invalid token");
    return str.substring(1, str.length() - 1);
  }
}
