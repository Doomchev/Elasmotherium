package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Rules extends Base {
  public Category root, cElse;
  
  public final HashMap<String, SymbolMask> masks = new HashMap<>();
  public SymbolMask getMask(String name) {
    SymbolMask mask = masks.get(name);
    if(mask == null) lineError("Cannot find symbol mask \"" + name + "\"");
    return mask;
  }
  
  public final HashMap<String, Category> categories = new HashMap<>();
  public final HashMap<String, Error> errors = new HashMap<>();
  public Category getCategory(String name) {
    Category symbol = categories.get(name);
    if(symbol == null) {
      symbol = new Category(name);
      categories.put(name, symbol);
    }
    return symbol;
  }
  
  private BufferedReader reader;
  public Rules load(String fileName) {
    masks.clear();
    masks.put("tab", new SymbolMask('\t'));
    masks.put("space", new SymbolMask(' '));
    masks.put("newline", new SymbolMask('\n').set('\r'));
    masks.put("eof", new SymbolMask(129));
    categories.clear();
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
        if(line.contains(".priority")) {
          String[] parts = line.split("=");
          parts[0] = parts[0].split("\\.")[0];
          getCategory(parts[0].trim()).priority = Integer.parseInt(parts[1].trim());
        } else if(equalPos >= 0 && (equalPos < colonPos || colonPos < 0)) {
          SymbolMask mask = new SymbolMask();
          for(String symbol : line.substring(equalPos + 1).trim().split(" ")) {
            if(symbol.length() == 1) {
              mask.set(symbol.charAt(0));
            } else if(symbol.length() == 3 && symbol.charAt(1) == '-') {
              mask.set(symbol.charAt(0), symbol.charAt(2));
            } else if(symbol.length() >= 2) {
              SymbolMask mask2 = masks.get(symbol);
              if(mask2 == null) lineError("Mask \"" + symbol + "\" is not found");
              mask.or(mask2);
            }
          }
          masks.put(line.substring(0, equalPos).trim(), mask);
        } else if(line.startsWith("ERROR ")) {
          String[] parts = line.substring(6).split(":");
          errors.put(parts[0].trim(), new Error(parts[1].trim()));
        } else {
          if(colonPos < 0 && equalPos < 0) lineError(": or = expected");
          Category category = getCategory(line.substring(0, colonPos).trim());
          if(category.action != null)
            lineError("Category \"" + category.name + "\" is already defined");
          category.action = actionChain(line.substring(colonPos + 1), null);
        }
      }
    } catch (FileNotFoundException ex) {
      error(fileName + " not found.");
    } catch (IOException ex) {
      error(fileName + "Cannot read " + fileName + ".");
    }
    
    root = getCategory("root");
    cElse = getCategory("elseOp");
    return this;
  }

  private Action actionChain(String commands, Action lastAction)
      throws IOException {
    Action firstAction = null, currentAction = null;
    boolean exit = false;
    loop: for(String command : commands.trim().split(" ")) {
      Action action;
      int bracketPos = command.indexOf('(');
      String name = bracketPos < 0 ? command : command.substring(0, bracketPos);
      String params = bracketPos < 0 ? "" :command.substring(bracketPos + 1
          , command.length() - 1);
      Node strucParam = null;
      int intParam = -1;
      if(!params.startsWith("\"")) {
        for(int n = 0; n < params.length(); n++) {
          char c = params.charAt(n);
          if(c >= '0' && c <= '9') continue;
          if(n == 0) {
            strucParam = toStructure(params);
          } else {
            if(c != ',') lineError("Comma expected");
            intParam = Integer.parseInt(params.substring(0, n));
            strucParam = toStructure(params.substring(n + 1));
          }
          break;
        }
        if(strucParam == null && !params.isEmpty())
          intParam = Integer.parseInt(params);
      }

      //System.out.println(name);
      switch(name) {
        case "RETURN":
          action = new ActionReturn(strucParam);
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
        case "INSERT":
          if(intParam < 0 || strucParam == null)
              lineError("INSERT requires 2 parameters");
          action = new ActionInsert(intParam, strucParam);
          break;
        case "STORE":
          action = new ActionStore(intParam);
          break;
        case "PROCESS":
          action = new ActionProcess(this, intParam);
          break;
        case "EXPECT":
          action = new ActionExpect(params.charAt(1));
          break;
        case "CREATE":
          action = new ActionCreate(intParam, strucParam);
          break;
        case "CLEAR":
          action = new ActionClear();
          break;
        case ">>":
          action = new ActionForward();
          break;
        case "{":
          String line;
          ActionSwitchSymbol switchSymbol = new ActionSwitchSymbol();
          Action back = new ActionGoToAction(switchSymbol);
          while(true) {
            if((line = reader.readLine()) == null)
              lineError("Unexpected end of file");
            lineNum++;
            line = line.trim();
            if(line.equals("}")) break;
            int colonPos = line.indexOf(':');
            String mask = line.substring(0, colonPos).trim();
            Action symbolAction = actionChain(line.substring(colonPos + 1), back);
            if(mask.startsWith("\"")) {
              if(mask.length() != 3) lineError("Invalid token");
              switchSymbol.setAction(stringParam(mask).charAt(0), symbolAction);
            } else if(mask.equals("other")) {
              switchSymbol.setAction(symbolAction);
            } else {
              SymbolMask symbolMask = getMask(mask);
              if(symbolMask == null)
                lineError("Mask \"" + mask + "\" is not found");
              switchSymbol.setAction(symbolMask, symbolAction);
            }
          }
          action = switchSymbol;
          exit = true;
          break;
        case "SWITCH":
          ActionSwitchToken switchToken = new ActionSwitchToken(intParam);
          back = new ActionGoToAction(switchToken);
          while(true) {
            if((line = reader.readLine()) == null)
              lineError("Unexpected end of file");
            lineNum++;
            line = line.trim();
            if(line.equals("}")) break;
            int colonPos = line.indexOf(':');
            if(intParam < 0) lineError("No integer parameter in SWITCH");
            String token = line.substring(0, colonPos).trim();
            Action tokenAction = actionChain(line.substring(colonPos + 1), back);
            if(token.startsWith("\"")) {
              if(!token.endsWith("\""))
                lineError("Invalid token");
              switchToken.addEntry(stringParam(token), tokenAction);
            } else if(token.equals("other")) {
              switchToken.defaultAction = tokenAction;
            } else {
              lineError("Invalid token");
            }
          }
          action = switchToken;
          exit = true;
          break;
        default:
          Error error = errors.get(name);
          if(error == null) {
            //System.out.println(name);
            if(bracketPos < 0) {
              action = new ActionGoToCategory(getCategory(name));
            } else {
              action = new ActionSub(getCategory(name), intParam);
            }
          } else {
            if(!params.isEmpty()) error = error.derive(params.replace("_"," "));
            action = error;
          }
      }
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
    if(start == pos) lineError("Integer number expected");
    return Integer.parseInt(strucString.substring(start, pos));
  }
  
  private Node toStructure(String params) {
    strucString = params + "]";
    pos = 0;
    return fillStructure(null);
  }
  
  private void setNodeParam(Node node, int tokStart) {
    if(tokStart < 0 || tokStart >= pos - 1) return;
    if(node.type == null) {
      node.type = getCategory(strucString.substring(tokStart, pos - 1));
    } else if(node.caption.isEmpty()) {
      node.caption = strucString.substring(tokStart, pos - 1);
    }
  }
  
  private Node fillStructure(Node parent) {
    int tokStart = pos;
    Node node = new Node(null);
    while(true) {
      if(pos >= strucString.length()) lineError("Unexpected end of structure");
      char c = strucString.charAt(pos);
      pos++;
      switch(c) {
        case '\\':
          if(node.type == null) {
            node = new StoredNode(readNum());
          } else {
            node = new StoredNodeValue(readNum(), node.type);
          }
          tokStart = -1;
          break;
        case ':':
          if(node.type != null || tokStart < 0) lineError("Unexpected :");
          setNodeParam(node, tokStart);
          tokStart = pos;
          break;
        case ',':
          if(parent == null) {
            lineError("Unexpected comma");
          } else {
            parent.add(node);
          }
          setNodeParam(node, tokStart);
          node = new Node(null);
          tokStart = pos;
          break;
        case ']':
          setNodeParam(node, tokStart);
          if(parent != null) parent.add(node);
          return node;
        case '[':
          setNodeParam(node, tokStart);
          fillStructure(node);
          tokStart = pos;
          break;
        default:
          if(c >= 'A' || c <= 'Z') break;
          if(c >= 'a' || c <= 'z') break;
          if(c >= '0' || c <= '9') break;
          if(c == '_') break;
          lineError("Syntax error");
      }
    }
  }

  private String stringParam(String str) {
    return str.substring(1, str.length() - 1);
  }
}
