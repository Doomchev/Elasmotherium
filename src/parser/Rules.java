package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import ast.Entity;
import ast.EntityStack;
import ast.Function;
import ast.ID;
import base.ElException;
import java.util.LinkedList;

public class Rules extends ParserBase {
  public Sub root;
  
  public final HashMap<String, SymbolMask> masks = new HashMap<>();
  public SymbolMask getMask(String name) throws ElException {
    SymbolMask mask = masks.get(name);
    if(mask == null) throw new ElException("Cannot find symbol mask \""
        + name + "\"");
    return mask;
  }
  
  public final HashMap<String, Sub> subs = new HashMap<>();
  public final HashMap<String, Error> errors = new HashMap<>();
  public final LinkedList<String> defSym = new LinkedList<>();
  
  public Sub getSub(String name) {
    Sub sub = subs.get(name);
    if(sub == null) {
      sub = new Sub(name, null);
      subs.put(name, sub);
    }
    return sub;
  }
  
  private BufferedReader reader;
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
              if(mask2 == null) throw new ElException("Mask \"" + symbol
                  + "\" is not found");
              mask.or(mask2);
            }
          }
          masks.put(line.substring(0, equalPos).trim(), mask);
        } else if(line.startsWith("ERROR ")) {
          String[] parts = line.substring(6).split(":");
          errors.put(parts[0].trim(), new Error(parts[1].trim()));
        } else if(line.startsWith("DEFAULT ")) {
          defSym.add(line.substring(8));
        } else {
          if(colonPos < 0 && equalPos < 0)
            throw new ElException(": or = expected");
          String name = line.substring(0, colonPos).trim();
          Sub sub = getSub(name);
          if(sub.action != null) throw new ElException("Sub \"" + name
              + "\" is already defined");
          sub.action = actionChain(line.substring(colonPos + 1), null, sub);
        }
      }
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", fileName + "Cannot read " + fileName + ".");
    } catch (ElException ex) {
      error("Error in ruleset", currentFileName + " (" + lineNum + ":"
          + (textPos - lineStart) + ")\n" + ex.message);
    }
    
    root = subs.get("root");
    return this;
  }
  
  public static LinkedList<String> listSplit(String commands, char delimiter) {
    LinkedList<String> commandList = new LinkedList<>();
    int start = -1, brackets = 0;
    boolean quotes = false;
    for(int i = 0; i < commands.length(); i++) {
      char c = commands.charAt(i);
      if(brackets == 0 && start < 0 && c != delimiter) start = i;
      if(c == '"') quotes = !quotes;
      if(quotes) continue;
      if(c == '(') {
        brackets++;
      } else if(c == ')') {
        brackets--;
      } else if(c == delimiter) {
        if(brackets > 0 || start < 0) continue;
        commandList.add(commands.substring(start, i).trim());
        start = -1;
      }
    }
    if(start >= 0) commandList.add(commands.substring(start).trim());
    //System.out.println(commands + " = " + listToString(commandList));
    return commandList;
  }
  
  private Action actionChain(String commands, Action lastAction, Sub currentSub)
      throws IOException, ElException {
    //System.out.println(commands);
    Action firstAction = null, currentAction = null;
    boolean exit = false;
    for(String command : listSplit(commands, ' ')) {
      Action action;
      int bracketPos = command.indexOf('(');
      String name = bracketPos < 0 ? command : command.substring(0, bracketPos);
      String params = bracketPos < 0 ? "" : command.substring(bracketPos + 1
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
          if(params.length() != 3) throw new ElException(
              "EXPECT command requires one symbol" + " as parameter");
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
              EntityStack stack = EntityStack.all.get(id);
              Function function = Function.all.get(id);
              if(stack == null) {
                if(function == null) {
                  function = new Function(id);
                  function.priority = 0;
                  Function.all.put(id, function);
                }
                stack = EntityStack.call;
              }
              if(stack == EntityStack.block || stack == EntityStack.constant) {
                if(param.length != 2) throw new ElException("CREATE "
                    + stack.name.string + " command requires 2 parameters");
                action = new ActionCreate(stack, ID.get(param[1]), null);
              } else {
                if(param.length != 1) throw new ElException(
                    "CREATE command requires single parameter");
                action = new ActionCreate(stack, null, function);
              }
            }
          }
          break;
        case "DUP":
          action = new ActionDup(EntityStack.all.get(ID.get(params.trim())));
          break;
        case "COPY":
          copy = true;
        case "MOVE":
          param = params.split(",");
          EntityStack<Entity> stack0 = EntityStack.all.get(ID.get(param[0]));
          if(stack0 == null) {
            ID id0 = ID.get(param[0]);
            Function function = Function.all.get(id0);
            if(function == null) {
              function = new Function(id0);
              function.priority = 0;
              Function.all.put(id0, function);
            }
            action = new ActionMoveNewFunction(function, EntityStack.get(
                param[1]));
          } else {
            if(param.length == 1) {
              action = new ActionMove(stack0, stack0, copy);
            } else {
              if(param.length != 2) throw new ElException(
                  "MOVE command requires 2 parameters");
              action = new ActionMove(stack0, EntityStack.get(param[1]), copy);
            }
          }
          break;
        case "SET":
          param = params.split(",");
          if(param.length != 2) throw new ElException(
              "SET command requires 2 parameters");
          action = new ActionSet(ID.get(param[0]), EntityStack.get(param[1]));
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
          for(String string: defSym)
            parseLine(string, switchAction, back, currentSub, true);
          while(true) {
            if((line = reader.readLine()) == null)
              throw new ElException("Unexpected end of file");
            lineNum++;
            line = line.trim();
            if(line.isEmpty() || line.startsWith("//")) continue;
            if(line.equals("}")) break;
            parseLine(line, switchAction, back, currentSub, name.equals("{"));
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
      //action.parserLine = lineNum;
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
  
  private void parseLine(String line, ActionSwitch switchAction, Action back
      , Sub currentSub, boolean isSymbolSwitch) throws ElException, IOException {
    LinkedList<String> parts = listSplit(line, ':');
    if(parts.size() < 2) throw new ElException(": expected");
    Action actionChain = actionChain(parts.getLast(), back, currentSub);
    for(String token: listSplit(parts.getFirst(), ',')) {
      if(token.startsWith("\"")) {
        switchAction.setStringAction(stringParam(token), actionChain);
      } else if(token.equals("other")) {
        switchAction.setOtherAction(actionChain);
      } else if(isSymbolSwitch) {
        SymbolMask symbolMask = getMask(token);
        if(symbolMask == null) throw new ElException("Mask \"" + token
            + "\" is not found");
        switchAction.setMaskAction(symbolMask, actionChain);
        //break;
      } else {
        throw new ElException("Invalid token");
      }
    }
  }

  private String strucString;
  private int pos;
  
  private int readNum() throws ElException {
    int start = pos;
    while(true) {
      char c = strucString.charAt(pos);
      if(c < '0' || c > '9') break;
      pos++;
    }
    if(start == pos) throw new ElException("Integer number expected");
    return Integer.parseInt(strucString.substring(start, pos));
  }

  private String stringParam(String str) throws ElException {
    if(!str.endsWith("\"") || str.length() < 2) throw new ElException(
        "Invalid token");
    return str.substring(1, str.length() - 1);
  }
}
