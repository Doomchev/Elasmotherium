package base;

import parser.structure.ClassEntity;
import parser.structure.Entity;
import parser.structure.Function;
import parser.structure.FunctionCall;
import parser.structure.ID;
import parser.structure.Variable;
import vm.VMInput;
import vm.VMPrint;
import vm.VMRandom;
import vm.VMShowMessage;

public class Processor extends Base {
  public static ID getID = ID.get("get"), mainID = ID.get("main")
      , publicID = ID.get("public"), staticID = ID.get("static");
      
  
  @SuppressWarnings("null")
  public static void process() {
    for(ClassEntity classEntity : ClassEntity.all.values())
      globalScope.add(classEntity);
    
    main.addToScope(globalScope);
    for(ClassEntity classEntity : ClassEntity.all.values())
      classEntity.addToScope(globalScope);
    
    addFunction(new Function(ID.get("print")) {
      @Override
      public void functionToByteCode(FunctionCall call) {
        addCommand(new VMPrint());
      }      
    }, ClassEntity.voidClass, ClassEntity.stringClass);
    
    addFunction(new Function(ID.get("input")) {
      @Override
      public void functionToByteCode(FunctionCall call) {
        addCommand(new VMInput());
      }      
    }, ClassEntity.stringClass, ClassEntity.stringClass);
    
    addFunction(new Function(ID.get("random")) {
      @Override
      public void functionToByteCode(FunctionCall call) {
        addCommand(new VMRandom());
      }      
    }, ClassEntity.i64Class, ClassEntity.i64Class);
    
    addFunction(new Function(ID.get("showMessage")) {
      @Override
      public void functionToByteCode(FunctionCall call) {
        addCommand(new VMShowMessage());
      }      
    }, ClassEntity.voidClass, ClassEntity.stringClass);
    
    System.out.println();
    System.out.println("Global scope:");
    globalScope.log("  ");
    System.out.println("Main scope:");
    main.code.scope.log("  ");
    
    main.type = Entity.voidClass;
    main.setTypes(null);
    for(ClassEntity classEntity : ClassEntity.all.values())
      classEntity.setTypes(globalScope);
  }

  private static void addFunction(Function function, ClassEntity returnType
      , ClassEntity... parameters) {
    function.type = returnType;
    function.isNativeFunction = true;
    for(ClassEntity type : parameters) {
      Variable variable = new Variable(ID.variableID);
      variable.type = type;
      function.parameters.add(variable);
    }
    globalScope.add(function, function.name);
  }
  
  public static void error(String message) {
    error("Processing code error in " + currentFileName, message);
  }
}
