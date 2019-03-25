package parser.structure;

import base.Base;
import java.util.Arrays;
import java.util.LinkedList;
import parser.Category;
import parser.ParserBase;

public class Node extends ParserBase {
  public Node parent;
  public Category category;
  public String caption;
  public Structure structure;
  public LinkedList<Node> children = new LinkedList<>();

  public Node(Category category, String caption, Node... nodes) {
    this.category = category;
    this.caption = caption;
    this.children.addAll(Arrays.asList(nodes));
  }
  
  public Node(Category category, Node... nodes) {
    this(category, "", nodes);
  }
  
  public Node(Category category, String caption) {
    this.category = category;
    this.caption = caption;
  }
  
  public Node(Category category) {
    this(category, "");
  }
  
  public Scope getScope() {
    return structure == null ? null : structure.toFunction();
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

  public boolean hasChild(Category category) {
    return findChild(category) != null;
  }

  public Node findChild(Category category) {
    for(Node child : children) if(child.category == category) return child;
    return null;
  }
  
  public Node getChild(Category category) {
    Node child = findChild(category);
    return child == null ? add(new Node(category)) : child;
  }
  
  public void removeChild(Category category) {
    children.remove(findChild(category));
  }
  
  public void moveTo(Node node) {
    parent.children.remove(this);
    node.add(this);
  }
  
  public void log(String spaces) {
    System.out.println(spaces + (category == null ? "" : category.name) + ": "
        + caption + (structure == null ? "" : " <" + structure.toPath() + ">"));
    for(Node child : children) child.log(spaces + " ");    
  }
  
  public Node resolve() {
    Node node = new Node(category, caption);
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
    return (category == null ? "null" : category.name) + (caption.isEmpty() ? "" : ":"
        + caption) + (str.isEmpty() ? "" : "[" + str + "]");
  }
  
  public static void error(String message) {
    Base.error("Resolving error", message);
  }
}
