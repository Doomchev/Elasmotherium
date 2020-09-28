package base;

import java.io.File;
import parser.ParserBase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import parser.Action;
import parser.Rules;
import ast.Code;
import ast.Entity;
import ast.EntityStack;

public class Module extends ParserBase {
  public static Module current;
  
  public String fileName;
  public final LinkedList<Module> modules = new LinkedList<>();
  public Code mainCode = new Code();

  public Module(String fileName) {
    this.fileName = fileName;
  }
  
  public static Module read(String fileName, Rules rules) {
    return new Module(fileName).read(rules);
  }
  
  public Module read(Rules rules) {
    current = this;
    path = Paths.get(fileName).getParent().toString() + "/";
    
    include(fileName);
    
    Action action = rules.root.action;
    while(action != null) action = action.execute();
    
    for(Module module : modules) module.read(rules);
    
    for(Entity entity : EntityStack.code.pop().lines)
      main.code.lines.add(entity);
    
    return this;
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
