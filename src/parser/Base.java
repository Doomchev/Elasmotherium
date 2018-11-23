package parser;

import java.util.Stack;
import javax.swing.JOptionPane;
import static parser.Module.lineNum;

public class Base {
  public static String prefix, text;
  public static int tokenStart, textPos, textLength, lineNum, lineStart;
  public static char currentChar;
  public static final boolean log = true;
  public static Scope currentScope;
  public static final Stack<Scope> scopes = new Stack<>();
  
  public static class Scope {
    public Category category;
    public ActionSub sub;
    public int returnIndex;
    public Node variables[] = new Node[5];

    public Scope(Category category, ActionSub sub) {
      this.category = category;
      this.sub = sub;
    }
  }
  
  public void incrementTextPos() {
    if(text.charAt(textPos) == '\n') {
      lineNum++;
      lineStart = textPos;
    }
    textPos++;
  }
  
  public static void error(String message) {
    JOptionPane.showMessageDialog(null, message, "Parsing error"
        , JOptionPane.ERROR_MESSAGE);
    throw new RuntimeException();
  }
  
  public static void lineError(String message) {
    error(message + " at line " + lineNum);
  }
  
  public static void columnError(String message) {
    error(message + " at line " + lineNum + " column " + (textPos - lineStart));
  }
}
