package base;

import vm.AskInt;
import ast.ClassEntity;
import java.io.File;
import parser.ParserBase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import parser.Action;
import parser.Rules;
import ast.EntityStack;
import ast.Function;
import ast.ID;
import vm.*;

public class Module extends ParserBase {
  public static ID id = ID.get("module");
  public static Module current;
  public static int lineNum;
  
  public String fileName;
  public final LinkedList<Module> modules = new LinkedList<>();
  public Function function = new Function(null);

  public Module(String fileName) {
    this.fileName = fileName;
  }
  
  public static Module read(String fileName, Rules rules) {
    return new Module(fileName).read(rules);
  }
  
  public Module read(Rules rules) {
    current = this;
    currentFunction = function;
    path = Paths.get(fileName).getParent().toString() + "/";
    
    include(fileName);
    
    if(log) printChapter("Parsing " + fileName);
    
    Action.currentAction = rules.root.action;
    try {
      while(Action.currentAction != null)
        Action.currentAction.execute();
    
      for(Module module : modules) module.read(rules);
    
      function.code = EntityStack.code.pop();
      function.allocation = Math.max(function.allocation, currentAllocation);
      //println(allocations.toString());
      //println(functions.toString());
    } catch (base.ElException ex) {
      error("Parsing error", currentFileName + " (" + lineNum + ":"
        + (textPos - lineStart) + ")\n" + ex.message);
    }
    
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
  
  public void newFunc(VMCommand command, ClassEntity returnType, String name
      , ClassEntity... paramTypes) {
    addToScope(new Function(command, returnType, name, paramTypes));
  }
  
  public void newFunc(VMCommand command, String name
      , ClassEntity... paramTypes) {
    addToScope(new Function(command, name, paramTypes));
  }
  
  public void process() throws ElException {
    currentFunction = function;
    
    addToScope(ClassEntity.Int);
    addToScope(ClassEntity.Bool);
    addToScope(ClassEntity.String);
    
    newFunc(new Print(), "print", ClassEntity.String);
    newFunc(new AskInt(), ClassEntity.Int, "askInt", ClassEntity.String);
    newFunc(new RandomInt(), ClassEntity.Int, "randomInt", ClassEntity.Int);
    newFunc(new Tell(), "tell", ClassEntity.String);
    newFunc(new Exit(), "exit");
    
    VMBase.append(new Allocate(function.allocation));
    function.code.processWithoutScope();
    VMBase.append(new Exit());
  }

  public void print() {
    if(log) printChapter("Abstract syntax tree");
    function.code.print("", fileName + ":" + function.allocation + " ");
  }
}
