package ast;

import base.ElException;
import java.util.LinkedList;
import java.util.Stack;

public class Formula extends Entity {
  private static final Function elseOp = Function.all.get(ID.get("elseOp"));
  
  private final LinkedList<Value> chunks = new LinkedList<>();
  private final Stack<Value> valueStack = new Stack<>();
  private final Stack<FunctionCall> opStack = new Stack<>();
  
  public void add(Value value) {
    chunks.add(value);
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
    call.priority = VALUE;
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
    if(log) System.out.println(subIndent + listToString(chunks));
    if(chunks.size() == 1) return chunks.getFirst();
    for(Value entity : chunks) {
      int priority = entity.getPriority();
      if(priority == Value.VALUE) {
        valueStack.push(entity);
        if(log) System.out.println(subIndent + "PUSH " + entity.toString()
            + " TO VALUE STACK");
      } else {
        while(!opStack.empty()) {
          if(opStack.lastElement().getPriority() >= priority) {
            System.out.println(subIndent + opStack.lastElement().getPriority()
              + ">=" + priority);
            popOp();
          } else {
            break;
          }
        }
        opStack.push((FunctionCall) entity);
        if(log) System.out.println(subIndent + "PUSH " + entity.toString()
            + " TO OPERATOR STACK");
      }
    }
    
    while(!opStack.empty()) {
      popOp();
    }
    
    if(valueStack.size() != 1)
      throw new ElException("Syntax error");
    return valueStack.pop();
  }

  private void popOp() throws ElException {
    FunctionCall op = opStack.pop();
    if(valueStack.size() < 2)
      throw new ElException("Syntax error");
    if(op.getFunction() == null) {
      valueStack.pop().moveToFunctionCall(op);
      op.setName(valueStack.pop().getName());
      valueStack.push(op);
      if(log) System.out.println(subIndent + "PUSH VALUES TO FUNCTION "
          + op.toString());
    } else if (op.getFunction() == elseOp) {
      Value value = valueStack.pop();
      ((FunctionCall) valueStack.peek()).add(value);
      if(log) System.out.println(subIndent + "PUSH ELSEOP TO FUNCTION "
          + valueStack.peek().toString());
    } else {
      op.addFirst(valueStack.pop());
      op.addFirst(valueStack.pop());
      valueStack.push(op);
      if(log) System.out.println(subIndent + "PUSH VALUES TO OPERATOR "
          + op.toString());
    }
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
