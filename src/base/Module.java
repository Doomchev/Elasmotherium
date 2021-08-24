package base;

import vm.AskInt;
import ast.ClassEntity;
import ast.Entity;
import parser.ParserBase;
import java.util.LinkedList;
import parser.Rules;
import ast.Function;
import ast.FunctionCall;
import ast.ID;
import ast.Variable;
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
    Module module = new Module(fileName);
    rules.read(module);
    return module;
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
    for(ClassEntity classEntity: function.code.classes) {
      for(Function constructor: classEntity.constructors) {
        LinkedList<Entity> lines = constructor.code.lines;
        for(Variable param: constructor.parameters) {
          if(!param.isField()) continue;
          Variable field = classEntity.getField(param.name);
          if(field == null) throw new ElException("Field " + param.name
              + " is not found in ", classEntity);
          FunctionCall equate = new FunctionCall(Function.equate);
          equate.parameters.add(field);
          equate.parameters.add(param);
          lines.addFirst(equate);
          param.parentClass = null;
          param.type = field.type;
          param.value = null;
        }
      }
    }
    
    print();
    
    currentFunction = function;
    
    addToScope(ClassEntity.Int);
    addToScope(ClassEntity.Bool);
    addToScope(ClassEntity.String);
    
    newFunc(new Print(), "println", ClassEntity.String);
    newFunc(new AskInt(), ClassEntity.Int, "askInt", ClassEntity.String);
    newFunc(new RandomInt(), ClassEntity.Int, "randomInt", ClassEntity.Int);
    newFunc(new Tell(), "tell", ClassEntity.String);
    newFunc(new Exit(), "exit");
    
    if(log) printChapter("Processing");
    
    VMBase.appendLog(new Allocate(function.allocation));
    function.code.processWithoutScope(new Exit());
  }

  public void print() {
    if(log) printChapter("Abstract syntax tree");
    function.code.print("", fileName + ":" + function.allocation + " ");
  }
}
