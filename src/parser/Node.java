package parser;

import java.util.LinkedList;

public class Node extends Base {
  public Node parent;
  public Category type;
  public String caption;
  public LinkedList<Node> children = new LinkedList<>();

  public Node(Category type) {
    this.type = type;
    this.caption = "";
  }
  
  public Node(Category type, String caption) {
    this.type = type;
    this.caption = caption;
  }
  
  public Node add(Node node) {
    children.add(node);
    node.parent = this;
    return node;
  }
  
  public void log(String spaces) {
    System.out.println(spaces + (type == null ? "" : type.name) + ": " + caption);
    for(Node child : children) child.log(spaces + " ");    
  }
  
  public Node resolve() {
    Node node = new Node(type, caption);
    for(Node child : children) node.add(child.resolve());
    return node;
  }

  @Override
  public String toString() {
    String str = "";
    for(Node child: children) {
      if(!str.isEmpty()) str += ",";
      str += child.toString();
    }
    return (type == null ? "null" : type.name) + (caption.isEmpty() ? "" : ":"
        + caption) + (str.isEmpty() ? "" : "[" + str + "]");
  }
}
