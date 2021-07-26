package ast;

import base.ElException;
import java.util.LinkedList;
import java.util.Stack;

public class Formula extends Entity {
  public final static Function elseOp = Function.all.get(ID.get("elseOp"));
  
  public final LinkedList<Value> chunks = new LinkedList<>();

  @Override
  public ID getID() {
    return formulaID;
  }

  @Override
  public void move(Entity entity) throws ElException {
    entity.moveToFormula(this);
  }

  @Override
  public void moveToCode(Code code) throws ElException {
    code.lines.add(toValue());
  }
  
  @Override
  public void moveToStringSequence(StringSequence sequence) throws ElException {
    sequence.chunks.add(toValue());
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) throws ElException {
    call.parameters.add(toValue());
    call.priority = VALUE;
  }

  @Override
  public void moveToFormula(Formula formula) throws ElException {
    formula.chunks.addAll(chunks);
  }

  @Override
  public void moveToParameters(Parameters parameters) throws ElException {
    parameters.parameters.add(toValue());
  }

  @Override
  public void moveToVariable(Variable variable) throws ElException {
    variable.value = toValue();
  }

  @Override
  public void moveToList(ListEntity list) throws ElException {
    list.values.add(toValue());
  }
  
  
  private final Stack<Value> valueStack = new Stack<>();
  private final Stack<FunctionCall> opStack = new Stack<>();
  
  @Override
  public Value toValue() throws ElException {
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
        opStack.push(entity.toCall());
        if(log) System.out.println(subIndent + "PUSH " + entity.toString()
            + " TO OPERATOR STACK");
      }
    }
    
    while(!opStack.empty()) {
      popOp();
    }
    
    if(valueStack.size() != 1) throw new ElException("Syntax error");
    return valueStack.pop();
  }

  private void popOp() throws ElException {
    FunctionCall op = opStack.pop();
    if(valueStack.size() < 2) throw new ElException("Syntax error");
    if(op.function == null) {
      valueStack.pop().moveToFunctionCall(op);
      op.function = valueStack.pop();
      valueStack.push(op);
      if(log) System.out.println(subIndent + "PUSH VALUES TO FUNCTION "
          + op.toString());
    } else if (op.function == elseOp) {
      Value value = valueStack.pop();
      valueStack.peek().toCall().parameters.add(value);
      if(log) System.out.println(subIndent + "PUSH ELSEOP TO FUNCTION "
          + valueStack.peek().toString());
    } else {
      op.parameters.addFirst(valueStack.pop());
      op.parameters.addFirst(valueStack.pop());
      valueStack.push(op);
      if(log) System.out.println(subIndent + "PUSH VALUES TO OPERATOR "
          + op.toString());
    }
  }
  
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
