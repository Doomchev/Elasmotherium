package base;

import vm.texture.*;
import vm.function.*;
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

  public final String name, path;
  public final LinkedList<Module> modules = new LinkedList<>();
  public StaticFunction function = new StaticFunction(null);

  public Module(String path, String name) {
    this.name = name;
    this.path = path;
  }

  public int getAllocation() {
    return function.getAllocation();
  }

  public String getFileName() {
    return path + "/" + name + ".es";
  }
  
  public static Module read(String path, String name, Rules rules) {
    Module module = new Module(path, name);
    rules.read(module);
    return module;
  }
  
  private void newFunction(VMCommand command, int parametersQuantity)
      throws ElException {
    String functionName = decapitalize(command.getClass().getSimpleName());
    function.getFunction(ID.get(removeLastDigit(functionName))
        , parametersQuantity).setCommand(command);
  }

  private void newFunction(String className, String methodName
      , int parametersQuantity, VMCommand command) throws ElException {
    ClassEntity.get(className).getMethod(methodName, parametersQuantity)
        .setCommand(command);
  }

  private void newConstructor(String className, int parametersQuantity
      , VMCommand command, VMValue value) throws ElException {
    ClassEntity classEntity = ClassEntity.get(className);
    classEntity.getConstructor(parametersQuantity)
        .setCommand(command);
    classEntity.setValue(value);
  }
  
  public boolean hasModule(String name) {
    for(Module module: modules) if(module.name.equals(name)) return true;
    return false;
  }
  
  public void process() throws ElException {
    function.processConstructors();
    
    currentFunction = function;
    
    ClassEntity.Int.addToScope();
    ClassEntity.Float.addToScope();
    ClassEntity.Bool.addToScope();
    ClassEntity.String.addToScope();
    
    newFunction(new Println(), 1);
    
    newFunction(new AskInt(), 1);
    newFunction(new RandomInt(), 1);
    newFunction(new RandomInt2(), 2);
    
    newFunction(new Sqrt(), 1);
    newFunction(new Floor(), 1);
    
    newFunction(new Say(), 1);
    newFunction(new Exit(), 0);
    
    newFunction(new ScreenHeight(), 0);
    newFunction(new ScreenWidth(), 0);
    
    newFunction("List", "add", 1, new I64AddToList());
    newConstructor("Array", 1, new I64ArrayCreate(), new I64ArrayValue(0));
    
    if(hasModule("Texture")) {
      newConstructor("Texture", 1, new TextureCreate(), new Texture());
      newFunction("Texture", "draw", 8, new TextureDraw());
      newFunction("Texture", "width", 0, new TextureWidth());
      newFunction("Texture", "height", 0, new TextureHeight());
    }
    
    print();
    
    if(log) printChapter("Processing");
    
    function.processCode(new Exit());
    
    print();
  }

  public void execute(boolean showCommands) {
    VMBase.execute(showCommands, this);
  }

  public void print() {
    if(log) {printChapter("Abstract syntax tree");
      function.printAllocation(getFileName());
      printScope();
    }
  }
}
