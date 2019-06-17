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
  public static void process(Module module) {
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      for(Variable field : classEntity.variables) {
        if(field.hasFlag(getID)) {
          classEntity.variables.remove(field);
          Function method = new Function(field.name);
          classEntity.methods.add(method);
          if(field.code != null) {
            method.code = field.code;
          } else {
            method.code = new Code(new FunctionCall(EntityStack.ret, field.value));
          }
        }
      }
    }
    
    populateScopes(module);
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      for(Function method : classEntity.methods) {
        if(method.hasFlag(constructorID)) {
          method.name = classEntity.name;
          for(int n = 0; n < method.parameters.size(); n++) {
            Variable parameter = method.parameters.get(n);
            if(parameter.hasFlag(ID.thisID)) {
              Variable variable = new Variable(parameter.name);
              Variable field = classEntity.getVariable(parameter.name);
              if(field == null) error("field " + parameter.name + " of "
                  + classEntity.name.string + " in consructor is not found");
              variable.type = field.getType();
              Link link = new Link(field);
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
    Function main = new Function(mainID);
    main.flags.add(publicID);
    main.flags.add(staticID);
    main.type = ClassEntity.voidClass;
    Variable args = new Variable(ID.get("args"));
    args.type = new Type(ID.get("String[]"));
    main.parameters.add(args);
    main.code = module.main;
    mainClass.methods.add(main);
    
    ClassEntity.intClass.name = ID.get("int");
    ClassEntity.floatClass.name = ID.get("float");
    ClassEntity.voidClass.name = ID.get("void");
    ClassEntity.booleanClass.name = ID.get("boolean");
    
    LinkedList<Entity> mainCodeLines = module.main.lines;
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

  private static void populateScopes(Module module) {
    Scope mainScope = new Scope(null);
    module.main.scope = mainScope;
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      mainScope.add(classEntity);
    }
    module.main.addToScope(null);
    
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      classEntity.addToScope(mainScope);
    }
    
    ID randomID = ID.get("random");
    Function random = new Function(randomID);
    random.type = ClassEntity.intClass;
    ClassEntity.intClass.scope.entries.put(randomID, random);
    
    Function print = new Function(ID.get("System.out.println"));
    print.type = ClassEntity.voidClass;
    mainScope.entries.put(ID.get("print"), print);
    
    Function showMessage = new Function(ID.get("JOptionPane.showMessageDialog"));
    showMessage.type = ClassEntity.voidClass;
    mainScope.entries.put(ID.get("showMessage"), showMessage);
    
    Function enterString = new Function(ID.get("JOptionPane.showInputDialog"));
    enterString.type = ClassEntity.stringClass;
    mainScope.entries.put(ID.get("enterString"), enterString);
    
    module.main.setTypes(null);
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      classEntity.setTypes(mainScope);
    }
    
    System.out.println();
    System.out.println("Scope:");
    module.main.scope.log("  ");
  }
  
  public static void error(String message) {
    error("Processing code error in " + currentFileName, message);
  }
}
