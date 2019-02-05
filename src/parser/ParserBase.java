package parser;

import base.Base;
import parser.structure.Node;
import java.util.Stack;
import static base.Module.lineNum;

public class ParserBase extends Base {
  public static StringBuffer text;
  public static String prefix, path;
  public static int tokenStart, textPos, textLength, lineStart;
  public static char currentChar;
  public static final boolean log = true;
  public static ParserScope currentParserScope;
  public static final Stack<ParserScope> parserScopes = new Stack<>();
  
  public static class ParserScope {
    public Category category;
    public ActionSub sub;
    public int returnIndex;
    public Node variables[] = new Node[6];

    public ParserScope(Category category, ActionSub sub) {
      this.category = category;
      this.sub = sub;
    }
  }
  
  public static Stack<Include> includes = new Stack<>();
  public static class Include {
    StringBuffer text;
    String prefix, currentFileName;
    int textPos, tokenStart, lineNum, lineStart, textLength;

    public Include() {
      this.text = ParserBase.text;
      this.textLength = ParserBase.textLength;
      this.textPos = ParserBase.textPos;
      this.tokenStart = ParserBase.tokenStart;
      this.lineNum = ParserBase.lineNum;
      this.lineStart = ParserBase.lineStart;
      this.prefix = ParserBase.prefix;
      this.currentFileName = ParserBase.currentFileName;
    }
    
    public void load() {
      ParserBase.text = this.text;
      ParserBase.textLength = this.textLength;
      ParserBase.textPos = this.textPos;
      ParserBase.tokenStart = this.tokenStart;
      ParserBase.lineNum = this.lineNum;
      ParserBase.lineStart = this.lineStart;
      ParserBase.prefix = this.prefix;
      ParserBase.currentFileName = this.currentFileName;
    }
  }
  
  public void incrementTextPos() {
    if(text.charAt(textPos) == '\n') {
      lineNum++;
      lineStart = textPos;
    }
    textPos++;
    if(textPos >= textLength && !includes.isEmpty()) {
      includes.pop().load();
      if(log) System.out.println(" RETURN FROM INCLUDE");
    }
  }
  
  public static void error(String message) {
    error("Parsing code error", currentFileName + " (" + lineNum + ")\n"
        + message);
  }
}
