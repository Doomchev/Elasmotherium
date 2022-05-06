package base;

public class LineReader extends Reader {
  private java.io.BufferedReader reader;

  public LineReader(String fileName) {
    this.fileName = fileName;
    try {
      reader = new java.io.BufferedReader(new java.io.FileReader(fileName));
    } catch (java.io.FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    }
    lineNum = 0;
  }

  public String readLine() {
    try {
      while(true) {
        String line = reader.readLine();
        if(line == null) return null;
        lineNum++;
        line = line.trim();
        if(line.isEmpty() || line.startsWith("//")) continue;
        return line;
      }
    } catch (java.io.IOException ex) {
      error("I/O error", "Cannot read " + fileName + ".");
    }
    return null;
  }

  @Override
  public String getError() {
    return fileName + ", line " + lineNum;
  }

  public int getLineNum() {
    return lineNum;
  }

  public void log(String message, int line) {
    System.out.println(subIndent.toString() + line + ": " + message);
  }

  public void showDebugMessage(String message) {
    showDebugMessage(fileName + ": " + lineNum, message, readText(fileName)
        , lineNum);
  }
}
