package parser;

import base.Base;
import java.util.Stack;
import java.util.List;

public class ParserBase extends Base {
  public static StringBuffer text;
  public static String prefix, path;
  public static int tokenStart, textPos, textLength, lineStart;
  public static char currentChar;
  public static final Stack<ActionSub> returnStack = new Stack<>();
  
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
      this.lineNum = ParserBase.currentLineNum;
      this.lineStart = ParserBase.lineStart;
      this.prefix = ParserBase.prefix;
      this.currentFileName = ParserBase.currentFileName;
    }
    
    public void load() {
      ParserBase.text = this.text;
      ParserBase.textLength = this.textLength;
      ParserBase.textPos = this.textPos;
      ParserBase.tokenStart = this.tokenStart;
      ParserBase.currentLineNum = this.lineNum;
      ParserBase.lineStart = this.lineStart;
      ParserBase.prefix = this.prefix;
      ParserBase.currentFileName = this.currentFileName;
    }
  }
  
  public void incrementTextPos() {
    if(text.charAt(textPos) == '\n') {
      currentLineNum++;
      lineStart = textPos;
    }
    textPos++;
    if(textPos >= textLength && !includes.isEmpty()) {
      includes.pop().load();
      if(log) System.out.println(" RETURN FROM INCLUDE");
    }
  }
  
  public static String listToString(List<? extends Object> list) {
    return listToString(list, ", ");
  }
  
  public static String listToString(List<? extends Object> list, String delimiter) {
    String str = "";
    for(Object object : list) {
      if(!str.isEmpty()) str += delimiter;
      str += object.toString();
    }
    return str;
  }
}
