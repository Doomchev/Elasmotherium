package base;

import ast.ClassEntity;
import ast.Entity;
import ast.EntityStack;
import ast.Function;
import ast.FunctionCall;
import ast.ID;
import ast.Link;
import ast.Variable;
import vm.VMInput;
import vm.VMPrint;
import vm.VMRandom;
import vm.VMShowMessage;

public class Processor extends Base {
  public static ID getID = ID.get("get"), mainID = ID.get("main")
      , publicID = ID.get("public"), staticID = ID.get("static");
      
  
  @SuppressWarnings("null")
  public static void process() {
    // Converting this parameters in constructor to code lines
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      for(Function method : classEntity.methods) {
        if(!method.hasFlag(constructorID)) continue;
        method.name = classEntity.name;
        for(int n = 0; n < method.parameters.size(); n++) {
          Variable parameter = method.parameters.get(n);
          if(!parameter.hasFlag(ID.thisID)) continue;
          Variable variable = new Variable(parameter.name);
          Variable field = classEntity.getVariable(parameter.name);
          if(field == null) error("field " + parameter.name + " of "
              + classEntity.name.string + " in consructor is not found");
          variable.type = field.getType();
          Link varLink = new Link(variable);
          Link fieldLink = new Link(field);
          fieldLink.thisFlag = true;
          method.parameters.set(n, variable);
          method.code.lines.addFirst(new FunctionCall(EntityStack.equate
              , fieldLink, varLink));
        }
        method.type = classEntity;
      }
    }
    
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
