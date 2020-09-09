package parser.structure;

import java.util.LinkedList;
import java.util.Stack;

public class Formula extends Entity {
  public final static ID elseOpID = ID.get("elseOp");
  
  public final LinkedList<Value> chunks = new LinkedList<>();

  @Override
  public ID getID() {
    return formulaID;
  }

  @Override
  public void move(Entity entity) {
    entity.moveToFormula(this);
  }

  @Override
  void moveToCode(Code code) {
    code.lines.add(toValue());
  }
  
  @Override
  void moveToStringSequence(StringSequence sequence) {
    sequence.chunks.add(toValue());
  }

  @Override
  void moveToFunctionCall(FunctionCall call) {
    call.parameters.add(toValue());
  }

  @Override
  void moveToFormula(Formula formula) {
    formula.chunks.addAll(chunks);
  }

  @Override
  void moveToParameters(Parameters parameters) {
    parameters.parameters.add(toValue());
  }

  @Override
  void moveToVariable(Variable variable) {
    variable.value = toValue();
  }

  @Override
  void moveToFunction(Function function) {
    function.formula = toValue();
  }
  
  
  private final Stack<Value> valueStack = new Stack<>();
  private final Stack<FunctionCall> opStack = new Stack<>();
  private ID ifOp, elseOp;
  
  @Override
  public Value toValue() {
    if(log) System.out.println(listToString(chunks));
    if(chunks.size() == 1) return chunks.getFirst();
    for(Value entity : chunks) {
      int priority = entity.getPriority();
      if(entity.isEmptyFunction()) {
        while(!opStack.empty()) {
          if(opStack.lastElement().getPriority() >= priority) {
            popOp();
          } else {
            break;
          }
        }
        opStack.push(entity.toCall());
        if(log) System.out.println("PUSH " + entity.toString() + " TO OPERATOR STACK");
      } else {
        valueStack.push(entity);
        if(log) System.out.println("PUSH " + entity.toString() + " TO VALUE STACK");
      }
    }
    
    while(!opStack.empty()) {
      popOp();
    }
    
    if(valueStack.size() != 1) error("Syntax error");
    return valueStack.pop();
  }

  private void popOp() {
    FunctionCall op = opStack.pop();
    if(valueStack.size() < 2) error("Syntax error");
    if(op.function == null) {
      valueStack.pop().moveToFunctionCall(op);
      valueStack.pop().setFunction(op);
      valueStack.push(op);
      if(log) System.out.println("PUSH VALUES TO FUNCTION " + op.toString());
    } else if (op.function.getID() == elseOpID) {
      Value value = valueStack.pop();
      valueStack.peek().toCall().parameters.add(value);
      if(log) System.out.println("PUSH ELSEOP TO FUNCTION " + valueStack.peek().toString());
    } else {
      op.parameters.addFirst(valueStack.pop());
      op.parameters.addFirst(valueStack.pop());
      valueStack.push(op);
      if(log) System.out.println("PUSH VALUES TO OPERATOR " + op.toString());
    }
  }
  
  private void log() {
    System.out.print("\n    ");
    for(int i = 0; i < opStack.size(); i++) System.out.print(opStack.get(i).toString() + ", "); 
    System.out.print("\n    ");
    for(int i = 0; i < valueStack.size(); i++) System.out.print(valueStack.get(i).toString() + ", "); 
    System.out.println();
  }

  @Override
  public String toString() {
    return listToString(chunks);
  }
}
