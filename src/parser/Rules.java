package parser;

import ast.EntityStack;
import static base.Base.currentFunction;
import java.util.HashMap;
import base.ElException;
import base.Module;
import static base.Module.current;
import static base.Module.lineNum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import static parser.ParserBase.path;

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
  
  private EReader reader;
  public Rules load(String fileName) {
    currentFileName = fileName;
    masks.clear();
    masks.put("tab", new SymbolMask('\t'));
    masks.put("space", new SymbolMask(' '));
    masks.put("newline", new SymbolMask('\n').set('\r'));
    masks.put("eof", new SymbolMask(129));
    subs.clear();
    try {
      reader = new EReader(fileName);
      String line;
      while((line = reader.readLine()) != null) {
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
    } catch (ElException ex) {
      error("Error in ruleset", currentFileName + " (" + currentLineNum + ":"
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

  public static String stringParam(String str) throws ElException {
    if(!str.endsWith("\"") || str.length() < 2) throw new ElException(
        "Invalid token");
    return str.substring(1, str.length() - 1);
  }
  
  public static final HashMap<String, Action> actions = new HashMap<>();
  
  static {
    actions.put("RETURN", new ActionReturn());
    actions.put("SKIP", new ActionSkip());
    actions.put("SAVEPOS", new ActionSavePos());
    actions.put("LOADPOS", new ActionLoadPos());
    actions.put("ADD", new ActionAdd(""));
    actions.put("EXPECT", new ActionExpect(' '));
    actions.put("CLEAR", new ActionClear());
    actions.put("CREATE", new ActionCreate(null, null, null));
    actions.put("DUP", new ActionDup(null));
    actions.put("COPY", new ActionMove(null, null, true));
    actions.put("MOVE", new ActionMove(null, null, false));
    actions.put("SET", new ActionSet(null, null));
    actions.put("REMOVE", new ActionRemove(null));
    actions.put(">>", new ActionForward());
    actions.put("}", new ActionSwitchSymbol());
  }
  
  private Action actionChain(String commands, Action lastAction, Sub currentSub)
      throws ElException {
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
      switch(name) {
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
            if(line.equals("}")) break;
            parseLine(line, switchAction, back, currentSub, name.equals("{"));
          }
          action = switchAction;
          exit = true;
          break;
        default:
          action = actions.get(name);
          if(action != null) {
            action = action.create(params);
          } else {
            Error error = errors.get(name);
            if(error == null) {
              //System.out.println(name);
              if(bracketPos < 0) {
                action = new ActionGoToSub(getSub(name));
              } else {
                action = new ActionSub(getSub(name), currentSub
                    , params.isEmpty() ? null : getSub(params));
              }
            } else {
              if(!params.isEmpty()) error = error.derive(stringParam(params));
              action = error;
            }
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
      , Sub currentSub, boolean isSymbolSwitch) throws ElException {
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
  
  // reading module
  
  public void read(Module module) {
    String fileName = module.fileName;
    current = module;
    currentFunction = module.function;
    
    path = Paths.get(fileName).getParent().toString() + "/";
    
    include(fileName);
    
    if(log) printChapter("Parsing " + fileName);
    
    Action.currentAction = root.action;
    try {
      while(Action.currentAction != null)
        Action.currentAction.execute();
    
      for(Module module2: module.modules) read(module2);
    
      currentFunction.code = EntityStack.code.pop();
      currentFunction.allocation
          = Math.max(currentFunction.allocation, currentAllocation);
      //println(allocations.toString());
      //println(functions.toString());
    } catch (base.ElException ex) {
      error("Parsing error", currentFileName + " (" + lineNum + ":"
        + (textPos - lineStart) + ")\n" + ex.message);
    }
  }  
  
  public static void include(String fileName) {
    textPos = 0;
    tokenStart = 0;
    lineNum = 1;
    lineStart = -1;
    prefix = "";
    currentFileName = new File(fileName).getName();
    
    try {
      text = new StringBuffer(new String(Files.readAllBytes(Paths.get(fileName))
          , "UTF-8"));
      textLength = text.length();
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", "Cannot read " + fileName + ".");
    }
  }
}
