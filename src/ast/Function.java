package ast;

import static ast.Entity.addCommand;
import export.Chunk;
import java.util.LinkedList;
import java.util.ListIterator;
import vm.VMCall;
import vm.Command;
import vm.VMAllocate;
import vm.VMCallParam;
import vm.VMNewFunctionCall;
import vm.VMBase;
import vm.VMDeallocate;
import vm.VMReturnThis;
import vm.VMNewThis;

public class Function extends NamedEntity {
  public Code code = new Code();
  public Value formula;
  public Entity type = null;
  public final LinkedList<Variable> parameters = new LinkedList<>();
  public boolean isNativeFunction = false, isConstructor = false;
  public ClassEntity parentClass = null;
  public Chunk form = null;
  public int varIndex = -1, paramIndex = -1;
  public Command startingCommand;
  
  public Function(ID name) {
    this.name = name;
    addFlags();
  }
  
  @Override
  public ID getID() {
    return functionID;
  }

  @Override
  public Chunk getForm() {
    return form;
  }
  
  @Override
  public LinkedList<? extends Entity> getChildren() {
    return parameters;
  }

  @Override
  public Entity getType() {
    return type;
  }

  @Override
  public Entity getChild(ID id) {
    if(id == typeID) return type;
    if(id == codeID) return code;
    if(id == formulaID) return formula;
    return null;
  }
  
  @Override
  public void setFlag(ID flag) {
    if(flag == ID.nativeID) isNativeFunction = true;
    if(flag == ID.constructorID) isConstructor = true;
  }

  @Override
  public Function toFunction() {
    return this;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToFunction(this);
  }

  @Override
  public void moveToClass(ClassEntity classEntity) {
    parentClass = classEntity;
    classEntity.methods.add(this);
  }

  @Override
  public void moveToCode(Code code) {
    code.lines.add(this);
    code.functions.add(this);
  }

  @Override
  public void resolveLinks(Variables variables) {
    Function oldFunction = currentFunction;
    currentFunction = this;
    
    if(variables != null && variables.code != null)
      variables.code.functions.add(this);
    variables = new Variables(variables, null);
    for(Variable variable : parameters) {
      variable.type = variable.type.toClass();
      paramIndex++;
      variable.index = paramIndex;
      variables.list.addFirst(variable);
    }
    code.resolveLinks(variables);
    
    currentFunction = oldFunction;
  }

  @Override
  public void resolveLinks(FunctionCall call, Variables variables) {
    LinkedList<Entity> params = call.parameters;
    for(Entity param : params) param.resolveLinks(variables);
    type = type.toClass();
    call.type = type;
  }

  @Override
  public void toByteCode() {
  }
  
  @Override
  public void functionToByteCode() {
    for(Function function : code.functions) function.functionToByteCode();
    VMBase.currentFunction = this;
    VMBase.currentCommand = null;
    if(isConstructor) addCommand(new VMNewThis(type.toClass()));
    if(varIndex >= 0) addCommand(new VMAllocate(varIndex + 1));
    code.toByteCode();
    if(isConstructor) {
      int paramQuantity = VMBase.currentFunction.paramIndex
        + VMBase.currentFunction.varIndex + 2;
      if(paramQuantity > 0) addCommand(new VMDeallocate(paramQuantity));
      addCommand(new VMReturnThis());
    }
  }

  @Override
  public void toByteCode(FunctionCall call) {
    if(!isNativeFunction) {
      addCommand(new VMNewFunctionCall());
      if(!call.parameters.isEmpty()) addCommand(new VMCallParam());
    }    
    ListIterator<Variable> iterator = parameters.listIterator();
    for(Entity parameter : call.parameters) {
      Entity type = parameter.getType();
      parameter.toByteCode();
      if(!iterator.hasNext()) throw new Error("Too many parameters in "
          + getName());
      conversion(type.toClass(), iterator.next().type.toClass());
    }
    if(iterator.hasNext()) throw new Error("Too few parameters in "
        + getName());
    functionToByteCode(call);
    if(!isNativeFunction) addCommand(new VMCall(this));
    conversion(type, call.convertTo);
  }

  @Override
  public void functionToByteCode(FunctionCall call) {
  }
}
 