package base;

public class SymbolReader extends Reader {
  public static final char OTHER_CHAR = 128, END_OF_FILE = 129;
  private static int savedTextPos, savedLineNum, savedLineStart;
  
  private final StringBuffer text;
  private StringBuilder prefix = new StringBuilder();
  private final int textLength;
  private int textPos = 0, tokenStart = 0, lineStart = -1
      , entityStart = 0;

  public SymbolReader(StringBuffer text, String fileName) {
    this.text = text;
    this.fileName = fileName;
    this.textLength = text.length();
  }

  public int getTextPos() {
    return textPos;
  }

  public int getEntityStart() {
    return entityStart;
  }

  public void clear() {
    tokenStart = textPos;
    entityStart = textPos;
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

  private String getTextChunk() {
    return text.substring(tokenStart, textPos);
  }
  
  public void appendToPrefix() {
    if(tokenStart < textPos) prefix.append(getTextChunk());
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

  public String getString() {
    if(prefix.length() == 0) return getTextChunk();
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

  public void showDebugMessage(String message) {
    showDebugMessage("Parsing error", message, text.toString()
        , textPos - 1, textPos + 1);
  }
}
