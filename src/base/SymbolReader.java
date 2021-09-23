package base;

public class SymbolReader extends Reader {
  public static final char OTHER_CHAR = 128, END_OF_FILE = 129;
  
  public StringBuffer text;
  public StringBuilder prefix = new StringBuilder();
  public int tokenStart = 0, textPos = 0, textLength, lineStart = -1;
  static int savedTextPos, savedLineNum, savedLineStart;

  public SymbolReader(StringBuffer text, String fileName) {
    this.text = text;
    this.fileName = fileName;
    this.textLength = text.length();
  }

  public void clear() {
    tokenStart = textPos;
    if(prefix.length() > 0) prefix = new StringBuilder();
  }

  public char getChar() {
    if(textPos >= textLength) return END_OF_FILE;
    char currentChar = text.charAt(textPos);
    return currentChar < 128 ? currentChar : OTHER_CHAR;
  }

  public char charAt(int delta) {
    return text.charAt(textPos + delta);
  }
  
  public char nextSymbol() {
    char c = text.charAt(textPos);
    if(c == '\n') {
      lineNum++;
      lineStart = textPos;
    }
    textPos++;
    return c;
  }

  public void incrementTextPos(int delta) {
    textPos += delta;
  }
  
  public void appendToPrefix() {
    if(tokenStart < textPos) prefix.append(text.substring(tokenStart, textPos));
  }

  public void add(String string) {
    appendToPrefix();
    prefix.append(string);
  }

  public void skip() {
    appendToPrefix();
    nextSymbol();
    tokenStart = textPos;
  }

  public String getPrefix() {
    appendToPrefix();
    return prefix.toString();
  }

  public void loadPos() {
    textPos = savedTextPos;
    lineNum = savedLineNum;
    lineStart = savedLineStart;
  }

  public void savePos() {
    savedTextPos = textPos;
    savedLineNum = lineNum;
    savedLineStart = lineStart;
  }

  @Override
  public String getError() {
    return fileName + " (" + lineNum + ":" + (textPos - lineStart) + ")\n";
  }

  public void log(String message) {
    System.out.println(subIndent.toString() + lineNum + ":"
        + (textPos - lineStart) + ", " + message);
  }
}
