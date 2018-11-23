package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Module extends Base {
  public String fileName;
  public Node rootNode;

  public Module(String fileName) {
    this.fileName = fileName;
  }
  
  public static Module read(String fileName, Rules rules) {
    Module module = new Module(fileName);
   
    textPos = 0;
    tokenStart = 0;
    lineNum = 1;
    lineStart = -1;
    prefix = "";
    Action action = rules.root.action;
    currentScope = new Scope(rules.root, null);
    
    try {
      text = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
      textLength = text.length();
      while(action != null) action = action.execute();
    } catch (FileNotFoundException ex) {
      error(fileName + " not found.");
    } catch (IOException ex) {
      error(fileName + "Cannot read " + fileName + ".");
    }
    
    module.rootNode = currentScope.variables[0];
    
    return module;
  }  
}
