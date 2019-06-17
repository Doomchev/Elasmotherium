package base;

import java.io.File;
import parser.ParserBase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import parser.Action;
import parser.Rules;
import parser.structure.Code;
import parser.structure.Entity;
import parser.structure.EntityStack;

public class Module extends ParserBase {
  public String fileName;
  public Code main;

  public Module(String fileName) {
    this.fileName = fileName;
  }
  
  public static Module read(String fileName, Rules rules) {
    Module module = new Module(fileName);
    path = Paths.get(fileName).getParent().toString() + "/";
    
    include(fileName);
    
    Action action = rules.root.action;
    while(action != null) action = action.execute();
    
    module.main = EntityStack.code.pop();
    
    return module;
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
