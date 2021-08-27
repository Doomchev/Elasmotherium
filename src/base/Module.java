package base;

import vm.AskInt;
import ast.ClassEntity;
import parser.ParserBase;
import java.util.LinkedList;
import parser.Rules;
import ast.Function;
import ast.ID;
import vm.*;

public class Module extends ParserBase {
  public static final ID id = ID.get("module");
  public static Module current;

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
    ID id = ID.get(decapitalize(command.getClass().getSimpleName()));
    function.setFunctionCommand(id, command);
  }
  
  public void process() throws ElException {
    function.processConstructors();
    
    currentFunction = function;
    
    addToScope(ClassEntity.Int);
    addToScope(ClassEntity.Bool);
    addToScope(ClassEntity.String);
    
    newFunc(new Println());
    newFunc(new AskInt());
    newFunc(new RandomInt());
    newFunc(new Say());
    newFunc(new Exit());
    
    print();
    
    if(log) printChapter("Processing");
    
    function.appendAllocation();
    function.processCode(new Exit());
  }

  public void print() {
    if(log) printChapter("Abstract syntax tree");
    function.printAllocation(fileName);
  }
}
