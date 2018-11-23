package parser;

import java.util.Stack;

public class ActionProcess extends Action {
  Rules rules;
  int index;

  public ActionProcess(Rules rules, int index) {
    this.rules = rules;
    this.index = index;
  }
  
  private final Stack<Node> valueStack = new Stack<>();
  private final Stack<Node> opStack = new Stack<>();
  private Category elseOp;
  @Override
  public Action execute() {
    Node listNode = currentScope.variables[index];
    for(Node node : listNode.children) {
      Category type = node.type;
      if(type.priority == 0) {
        valueStack.push(node);
      } else {
        while(!opStack.empty()) {
          if(opStack.lastElement().type.priority >= type.priority) {
            popOp();
          } else {
            break;
          }
        }
        opStack.push(node);
      }
    }
    
    while(!opStack.empty()) popOp();
    
    listNode.children.clear();
    listNode.children.add(valueStack.pop());
    
    if(!valueStack.empty()) lineError("Syntax error");
    
    return nextAction;
  }

  private void popOp() {
    Node op = opStack.pop();
    if(valueStack.size() < 2) lineError("Syntax error");
    if(op.type == rules.cElse) {
      Node value = valueStack.pop();
      valueStack.peek().children.add(value);
    } else {
      op.children.addFirst(valueStack.pop());
      op.children.addFirst(valueStack.pop());
      valueStack.push(op);
    }
  }
}
