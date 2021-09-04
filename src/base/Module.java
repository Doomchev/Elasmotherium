package base;

import vm.functions.Say;
import vm.functions.Println;
import vm.functions.Sqrt;
import vm.functions.RandomInt;
import vm.functions.Floor;
import vm.functions.Exit;
import vm.functions.AskInt;
import ast.ClassEntity;
import parser.ParserBase;
import java.util.LinkedList;
import parser.Rules;
import ast.Function;
import ast.ID;
import vm.*;
import vm.i64.I64Add;
import vm.i64.I64AddToList;

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
  
  private void newFunc(VMCommand command) throws ElException {
    ID funcID = ID.get(decapitalize(command.getClass().getSimpleName()));
    function.setFunctionCommand(funcID, command);
  }

  private void newFunc(String className, String methodName, VMCommand command) {
    ClassEntity.get(className).getMethod(methodName).setCommand(command);
  }
  
  public void process() throws ElException {
    function.processConstructors();
    
    currentFunction = function;
    
    addToScope(ClassEntity.Int);
    addToScope(ClassEntity.Float);
    addToScope(ClassEntity.Bool);
    addToScope(ClassEntity.String);
    
    newFunc(new Println());
    
    newFunc(new AskInt());
    newFunc(new RandomInt());
    
    newFunc(new Sqrt());
    newFunc(new Floor());
    
    newFunc(new Say());
    newFunc(new Exit());
    
    newFunc("List", "add", new I64AddToList());
    
    print();
    
    if(log) printChapter("Processing");
    
    function.appendAllocation();
    function.processCode(new Exit());
    
    print();
  }

  public void print() {
    if(log) printChapter("Abstract syntax tree");
    function.printAllocation(fileName);
  }
}
