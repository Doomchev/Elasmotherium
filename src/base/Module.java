package base;

import vm.function.Exit;
import vm.function.RandomInt2;
import vm.function.AskInt;
import vm.function.Sqrt;
import vm.function.Floor;
import vm.function.Println;
import vm.function.Say;
import vm.function.RandomInt;
import ast.ClassEntity;
import parser.ParserBase;
import java.util.LinkedList;
import parser.Rules;
import ast.function.StaticFunction;
import ast.ID;
import vm.*;
import vm.collection.*;
import vm.i64.*;
import vm.values.*;
import vm.variables.*;

public class Module extends ParserBase {
  public static final ID id = ID.get("module");
  public static Module current;

  public String fileName;
  public final LinkedList<Module> modules = new LinkedList<>();
  public StaticFunction function = new StaticFunction(null);

  public Module(String fileName) {
    this.fileName = fileName;
  }

  public int getAllocation() {
    return function.getAllocation();
  }
  
  public static Module read(String fileName, Rules rules) throws ElException {
    Module module = new Module(fileName);
    rules.read(module);
    return module;
  }
  
  private void newFunc(VMCommand command, int parametersQuantity)
      throws ElException {
    String name = decapitalize(command.getClass().getSimpleName());
    function.getFunction(ID.get(removeLastDigit(name))
        , parametersQuantity).setCommand(command);
  }

  private void newFunc(String className, String methodName
      , int parametersQuantity, VMCommand command) throws ElException {
    ClassEntity.get(className).getMethod(methodName, parametersQuantity)
        .setCommand(command);
  }

  private void newFunc(String className, int parametersQuantity
      , VMCommand command) throws ElException {
    ClassEntity.get(className).getConstructor(parametersQuantity)
        .setCommand(command);
  }
  
  public void process() throws ElException {
    function.processConstructors();
    
    currentFunction = function;
    
    ClassEntity.Int.addToScope();
    ClassEntity.Float.addToScope();
    ClassEntity.Bool.addToScope();
    ClassEntity.String.addToScope();
    
    newFunc(new Println(), 1);
    
    newFunc(new AskInt(), 1);
    newFunc(new RandomInt(), 1);
    newFunc(new RandomInt2(), 2);
    
    newFunc(new Sqrt(), 1);
    newFunc(new Floor(), 1);
    
    newFunc(new Say(), 1);
    newFunc(new Exit(), 0);
    
    newFunc(new ScreenHeight(), 0);
    newFunc(new ScreenWidth(), 0);
    
    newFunc("List", "add", 1, new I64AddToList());
    newFunc("Array", 1, new I64ArrayCreate1());
    
    ClassEntity.get("Array").setValue(new I64ArrayValue(0));
    
    print();
    
    if(log) printChapter("Processing");
    
    function.processCode(new Exit());
    
    print();
  }

  public void execute(boolean showCommands) throws ElException {
    VMBase.execute(showCommands, this);
  }

  public void print() {
    if(log) {printChapter("Abstract syntax tree");
      function.printAllocation(fileName);
      printScope();
    }
  }
}
