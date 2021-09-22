package base;

import static base.Base.currentLineNum;
import static base.Log.error;

public class Reader {
  private java.io.BufferedReader reader;
  private String fileName;

  public Reader(String fileName) {
    this.fileName = fileName;
    try {
      reader = new java.io.BufferedReader(new java.io.FileReader(fileName));
    } catch (java.io.FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    }
    currentLineNum = 0;
  }

  public String readLine() {
    try {
      while(true) {
        String line = reader.readLine();
        if(line == null) return null;
        currentLineNum++;
        line = line.trim();
        if(line.isEmpty() || line.startsWith("//")) continue;
        return line;
      }
    } catch (java.io.IOException ex) {
      error("I/O error", "Cannot read " + fileName + ".");
    }
    return null;
  }
}
