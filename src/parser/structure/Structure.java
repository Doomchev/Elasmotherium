package parser.structure;

import base.Base;

public class Structure extends Base {
  Structure type;
  Scope parent;
  Structure[] typeParam = null;
  Node node;
  String nativeName = null;

  public Structure(Node node, Scope parent) {
    this.parent = parent;
    this.node = node;
    if(parent != null && !node.caption.isEmpty()) {
      if(parent.children.containsKey(node.caption)) error("System error"
          , "Child is already here");
      parent.children.put(node.caption, this);
    }
  }

  public ClassScope toClass() {
    return null;
  }

  public SubType toSubType() {
    return null;
  }
  
  public Scope toFunction() {
    return null;
  }
  
  public Scope getScope() {
    return parent;
  }
  
  public ClassScope resolve() {
    return type.resolve();
  }

  public Structure resolveType(Structure structure) {
    return this;
  }
  
  boolean isChildOf(ClassScope classScope) {
    return type.isChildOf(classScope);
  }

  Structure findType(String name) {
    return parent.findField(name);
  }

  Structure findField(String name) {
    return parent.findField(name);
  }

  Structure findVariable(String name) {
    return parent.findVariable(name);
  }

  
  public String log() {
    return log("");
  }

  String log(String indent) {
    return indent + typeStr() + " " + node.caption + "\n";
  }
  String typeStr() {
    if(type == null) {
      return "void";
    } else {
      String str = "";
      if(typeParam != null) {
        for(Structure param : typeParam) {
          if(!str.isEmpty()) str += ", ";
          str += param.typeStr();
        }
        str = "<" + str + ">";
      }
      return type.node.caption + str;
    }
  }

  @Override
  public String toString() {
    return typeStr() + " " + node.caption;
  }

  String toPath() {
    return parent.toPath() + "." + node.caption + (nativeName == null ? ""
        : "/" + nativeName);
  }
}
