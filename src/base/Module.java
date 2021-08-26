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
  
  public void newFunc(VMCommand command) throws ElException {
    ID id2 = ID.get(decapitalize(command.getClass().getSimpleName()));
    for(Function innerFunction: function.code.functions)
      if(innerFunction.name == id2) {
        innerFunction.command = command;
        return;
      }
    throw new ElException("Function " + id2 + " not found.");
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
    
    newFunc(new Println());
    newFunc(new AskInt());
    newFunc(new RandomInt());
    newFunc(new Tell());
    newFunc(new Exit());
    
    if(log) printChapter("Processing");
    
    VMBase.appendLog(new Allocate(function.allocation));
    function.code.processWithoutScope(new Exit());
  }

  public void print() {
    if(log) printChapter("Abstract syntax tree");
    function.code.print("", fileName + ":" + function.allocation + " ");
  }
}
