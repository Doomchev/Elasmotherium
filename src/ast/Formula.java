package ast;

import ast.function.FunctionCall;
import ast.function.NativeFunction;
import exception.ElException;
import exception.ElException.MethodException;
import java.util.LinkedList;
import java.util.Stack;

public class Formula extends Entity {
  private final LinkedList<Entity> chunks = new LinkedList<>();
  private final Stack<Value> valueStack = new Stack<>();
  private final Stack<NativeFunction> opStack = new Stack<>();

  public Formula() {
    super(0, 0);
  }
  
  public void add(Entity entity) {
    chunks.add(entity);
  }
  
  // moving functions

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToFormula(this);
  }

  @Override
  public void moveToCode(Code code) throws ElException {
    code.addLine(getFormulaValue());
  }
  
  @Override
  public void moveToStringSequence(StringSequence sequence) throws ElException {
    sequence.add(getFormulaValue());
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) throws ElException {
    call.add(getFormulaValue());
  }

  @Override
  public void moveToFormula(Formula formula) throws ElException {
    formula.chunks.addAll(chunks);
  }

  @Override
  public void moveToParameters(Parameters parameters) throws ElException {
    parameters.add(getFormulaValue());
  }

  @Override
  public void moveToVariable(Variable variable) throws ElException {
    variable.setValue(getFormulaValue());
  }

  @Override
  public void moveToList(ListEntity list) throws ElException {
    list.values.add(getFormulaValue());
  }
  
  // commands
  
  @Override
  public Value getFormulaValue() throws ElException {
    if(log) currentSymbolReader.log(listToString(chunks));
    if(chunks.size() == 1) return (Value) chunks.getFirst();
    for(Entity entity : chunks) {
      if(entity instanceof NativeFunction) {
        NativeFunction function = (NativeFunction) entity;
        int priority = function.priority;
        while(!opStack.empty()) {
          if(opStack.lastElement().priority >= priority) {
            if(log) currentSymbolReader.log(
                opStack.lastElement().priority + ">=" + priority);
            popOp();
          } else {
            break;
          }
        }
        if(log) currentSymbolReader.log("PUSH " + entity.toString()
            + " TO OPERATOR STACK");
        opStack.push(function);
      } else {
        if(log) currentSymbolReader.log("PUSH " + entity.toString()
            + " TO VALUE STACK");
        valueStack.push((Value) entity);
      }
    }
    
    while(!opStack.empty()) popOp();
    
    if(valueStack.size() != 1)
      throw new MethodException(this, "getFormulaValue", "Error in formula.");
    return valueStack.pop();
  }

  private void popOp() throws ElException {
    NativeFunction op = opStack.pop();
    if(valueStack.size() < 2)
      throw new MethodException(this, "popOp", "Syntax error");
    FunctionCall call = new FunctionCall(op);
    if(op == NativeFunction.callFunction) {
      valueStack.pop().moveToFunctionCall(call);
      call.setFunction(valueStack.pop());
      if(log) currentSymbolReader.log("PUSH VALUES TO FUNCTION "
          + op.toString());
    } else {
      Value value = valueStack.pop();
      valueStack.pop().moveToFunctionCall(call);
      value.moveToFunctionCall(call);
      if(log) currentSymbolReader.log("PUSH VALUES TO OPERATOR "
          + op.toString());
    }
    valueStack.push(call);
  }
  
  // other
  
  public void print(String indent) {
    System.out.print("\n    ");
    for(int i = 0; i < opStack.size(); i++)
      System.out.print(opStack.get(i).toString() + ", "); 
    System.out.print("\n    ");
    for(int i = 0; i < valueStack.size(); i++)
      System.out.print(valueStack.get(i).toString() + ", "); 
    System.out.println();
  }

  @Override
  public String toString() {
    return listToString(chunks);
  }
}
