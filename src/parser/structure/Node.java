package parser.structure;

import base.Base;
import java.util.Arrays;
import java.util.LinkedList;
import parser.Category;
import parser.ParserBase;

public class Node extends ParserBase {
  public Node parent;
  public Category type;
  public String caption;
  public LinkedList<Node> children = new LinkedList<>();

  public Node(Category type, String caption, Node... nodes) {
    this.type = type;
    this.caption = caption;
    this.children.addAll(Arrays.asList(nodes));
  }
  
  public Node(Category type, Node... nodes) {
    this(type, "", nodes);
  }
  
  public Node(Category type, String caption) {
    this.type = type;
    this.caption = caption;
  }
  
  public Node(Category type) {
    this(type, "");
  }
  
  public Node first() {
    return children.getFirst();
  }
  
  public Node last() {
    return children.getLast();
  }
  
  public Node add(Node node) {
    children.add(node);
    node.parent = this;
    return node;
  }

  public boolean hasChild(Category type) {
    return findChild(type) != null;
  }

  public Node findChild(Category type) {
    for(Node child : children) if(child.type == type) return child;
    return null;
  }
  
  public Node getChild(Category type) {
    Node child = findChild(type);
    return child == null ? add(new Node(type)) : child;
  }
  
  public void removeChild(Category type) {
    children.remove(findChild(type));
  }
  
  public void moveTo(Node node) {
    parent.children.remove(this);
    node.add(this);
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
  
  public static void error(String message) {
    Base.error("Resolving error", message);
  }
}
