package parser;

import parser.structure.Node;
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
    Node listNode = currentParserScope.variables[index];
    if(log) log("PROCESS " + listNode.toString());
    if(listNode.children.isEmpty()) return nextAction;
    for(Node node : listNode.children) {
      Category category = node.category;
      if(category.priority == 0 || !node.children.isEmpty()) {
        valueStack.push(node);
      } else {
        while(!opStack.empty()) {
          if(opStack.lastElement().category.priority >= category.priority) {
            popOp();
          } else {
            break;
          }
        }
        opStack.push(node);
      }
      if(log) log();
    }
    
    while(!opStack.empty()) {
      popOp();
      if(log) log();
    }
    
    if(valueStack.size() != 1) actionError("Syntax error");
    Node value = valueStack.pop();
    listNode.children = value.children;
    listNode.category = value.category;
    listNode.caption = value.caption;
    
    return nextAction;
  }

  private void popOp() {
    Node op = opStack.pop();
    if(valueStack.size() < 2) actionError("Syntax error");
    if(op.category == rules.cElse) {
      Node value = valueStack.pop();
      valueStack.peek().children.add(value);
    } else {
      op.children.addFirst(valueStack.pop());
      op.children.addFirst(valueStack.pop());
      valueStack.push(op);
    }
  }
  
  private void log() {
    System.out.print("\n    ");
    for(int i = 0; i < opStack.size(); i++) System.out.print(opStack.get(i).toString() + ", "); 
    System.out.print("\n    ");
    for(int i = 0; i < valueStack.size(); i++) System.out.print(valueStack.get(i).toString() + ", "); 
    System.out.println();
  }
}
