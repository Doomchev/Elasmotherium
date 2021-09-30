package base;

import vm.function.Assert;
import vm.texture.*;
import vm.function.*;
import ast.ClassEntity;
import parser.Rules;
import ast.function.StaticFunction;
import ast.ID;
import ast.Variable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.TreeSet;
import processor.Processor;
import vm.*;
import vm.collection.*;
import vm.i64.*;
import vm.values.*;
import vm.variables.*;

public class Module extends Base {
  public static final ID id = ID.get("module");
  public static Rules rules = new Rules("parsers/standard.parser").load();
  public static Processor processor = new Processor()
      .load("processors/standard.processor");
  public static Module current;

  public final String name, path;
  public final TreeSet<String> moduleNames = new TreeSet<>();
  public final Stack<Module> moduleStack = new Stack<>();
  public StaticFunction function = new StaticFunction(null);

  public Module() {
    this.name = "";
    this.path = "";
  }

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
  
  public static Module read(StringBuffer text) {
    return new Module().read(text, false);
  }
  
  public static Module read(String path, String name) {
    return new Module(path, name).read(null, true);
  }

  public void addModule(String name) {
    if(hasModule(name)) return;
    moduleStack.add(new Module(modulesPath, name));
    moduleNames.add(name);
  }
  
  public Module read(StringBuffer text, boolean base) {
    Module.current = this;
    currentFunction = function;
    if(text == null)
      readCode(getFileName());
    else
      readCode(text);
    currentFunction.setAllocation();
    if(base) addModule("Base");
    while(!moduleStack.isEmpty())
      readCode(moduleStack.pop().getFileName());
    return this;
  }  
  
  private void readCode(String fileName) {
    if(log) printChapter("Parsing " + fileName);
    rules.parseCode(readText(fileName), fileName);
  }
  
  private void readCode(StringBuffer text) {
    if(log) printChapter("Parsing text");
    rules.parseCode(text, "");
  }
  
  public StringBuffer readText(String fileName) {
    try {
      return new StringBuffer(new String(Files.readAllBytes(Paths.get(fileName))
          , "UTF-8"));
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", "Cannot read " + fileName + ".");
    }
    return null;
  }

  public static void execute(String examples, String name
      , boolean showCommands) {
    Module module = Module.read("examples", name);
    processor.process(module);
    module.execute(showCommands);
  }
  
  public static void execute(StringBuffer text) {
    Module module = Module.read(text);
    processor.process(module);
    module.execute(true);    
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
    for(String moduleName: moduleNames) if(moduleName.equals(name)) return true;
    return false;
  }
  
  public void process() throws ElException {
    function.processConstructors();
    
    currentFunction = function;
    
    ClassEntity.Int.addToScope();
    ClassEntity.Float.addToScope();
    ClassEntity.Bool.addToScope();
    ClassEntity.String.addToScope();
    
    if(hasModule("Base")) {
      newFunction(new Println(), 1);

      newFunction(new AskInt(), 1);
      newFunction(new RandomInt(), 1);
      newFunction(new RandomInt2(), 2);

      newFunction(new Say(), 1);
      newFunction(new Exit(), 0);

      newFunction(new ScreenHeight(), 0);
      newFunction(new ScreenWidth(), 0);

      newFunction("List", "add", 1, new I64AddToList());
      newConstructor("Array", 1, new I64ArrayCreate(), new I64ArrayValue(0));
    }
    
    if(hasModule("Math")) {
      newFunction(new Sqrt(), 1);
      newFunction(new Floor(), 1);
    }
    
    if(hasModule("Texture")) {
      newConstructor("Texture", 1, new TextureCreate(), new Texture());
      newFunction("Texture", "draw", 8, new TextureDraw());
      newFunction("Texture", "width", 0, new TextureWidth());
      newFunction("Texture", "height", 0, new TextureHeight());
    }
    
    StaticFunction assertFunction = new StaticFunction(ID.get("assert"));
    Variable parameter = new Variable(ID.get("value"));
    parameter.setType(ClassEntity.Bool);
    assertFunction.addParameter(parameter);
    assertFunction.setCommand(new Assert());
    function.add(assertFunction);
    
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
