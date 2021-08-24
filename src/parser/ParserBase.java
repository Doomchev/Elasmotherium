package parser;

import ast.EntityStack;
import base.Base;
import static base.Base.currentAllocation;
import static base.Base.currentFileName;
import static base.Base.currentFunction;
import static base.Base.error;
import static base.Base.log;
import static base.Base.printChapter;
import base.Module;
import static base.Module.current;
import static base.Module.lineNum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.List;

public class ParserBase extends Base {
  static StringBuffer text;
  static String prefix, path;
  static int tokenStart, textPos, textLength, lineStart;
  static char currentChar;
  static final Stack<ActionSub> returnStack = new Stack<>();
  
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
