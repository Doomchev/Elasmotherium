package base;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import parser.structure.ClassEntity;
import parser.structure.Code;
import parser.structure.Entity;
import parser.structure.EntityStack;
import parser.structure.Function;
import parser.structure.FunctionCall;
import parser.structure.ID;
import parser.structure.Link;
import parser.structure.Scope;
import parser.structure.Type;
import parser.structure.Variable;

public class Processor extends Base {
  public static ID getID = ID.get("get"), mainID = ID.get("main")
      , publicID = ID.get("public"), staticID = ID.get("static")
      , constructorID = ID.get("constructor");
  
  @SuppressWarnings("null")
  public static void process() {
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      for(Function method : classEntity.methods) {
        if(method.hasFlag(constructorID)) {
          method.name = classEntity.name;
          for(int n = 0; n < method.parameters.size(); n++) {
            Variable parameter = method.parameters.get(n);
            if(parameter.hasFlag(ID.thisID)) {
              Variable field = classEntity.getVariable(parameter.name);
              if(field == null) error("Field \"" + parameter.name
                  + "\" is not found in constructor of " + classEntity.name);
              method.parameters.set(n, field);
            }
          }
        }
      }
    }
    
    populateScopes();
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      for(Function method : classEntity.methods) {
        if(method.hasFlag(constructorID)) {
          method.name = classEntity.name;
          for(int n = 0; n < method.parameters.size(); n++) {
            Variable parameter = method.parameters.get(n);
            if(parameter.isClassField) {
              Variable variable = new Variable(parameter.name);
              variable.type = parameter.getType();
              Link link = new Link(parameter);
              link.thisFlag = true;
              method.parameters.set(n, variable);
              method.code.lines.addFirst(new FunctionCall(EntityStack.equate
                  , link, variable));
            }
          }
        }
      }
    }
    
    ClassEntity mainClass = new ClassEntity(ID.get("Main_"));
    Function mainFunction = new Function(mainID);
    mainFunction.flags.add(publicID);
    mainFunction.flags.add(staticID);
    mainFunction.type = ClassEntity.voidClass;
    Variable args = new Variable(ID.get("args"));
    args.type = new Type(ID.get("String[]"));
    mainFunction.parameters.add(args);
    mainFunction.code = main;
    mainClass.methods.add(mainFunction);
    
    ClassEntity.intClass.name = ID.get("int");
    ClassEntity.floatClass.name = ID.get("float");
    ClassEntity.voidClass.name = ID.get("void");
    ClassEntity.booleanClass.name = ID.get("boolean");
    
    LinkedList<Entity> mainCodeLines = main.lines;
    int n = 0;
    while(n < mainCodeLines.size()) {
      Entity entity = mainCodeLines.get(n);
      if(entity.getClass() == Function.class) {
        mainClass.methods.add((Function) entity);
        mainCodeLines.remove(n);
      } else {
        n++;
      }
    }
  }

  private static void populateScopes() {
    Scope mainScope = new Scope(null);
    main.scope = mainScope;
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      mainScope.add(classEntity);
    }
    main.addToScope(null);
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      classEntity.addToScope(mainScope);
    }
    
    main.setTypes(null);
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      classEntity.setTypes(mainScope);
    }
    
    System.out.println();
    System.out.println("Scope:");
    main.scope.log("  ");
  }
  
  public static void error(String message) {
    error("Processing code error in " + currentFileName, message);
  }
}
