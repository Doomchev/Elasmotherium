package parser.structure;

import base.Base;
import java.util.HashMap;

public class Scope extends Structure {
  final HashMap<String, Structure> children = new HashMap<>();
  Structure[] params;

  Scope(Node node, Scope parent) {
    super(node, parent);
  }

  public Scope() {
    super(null, null);
  }
  
  @Override
  public Scope getScope() {
    return this;
  }
  
  @Override
  public Scope toFunction() {
    return this;
  }

  Scope create(Node node, Scope scope) {
    return this;
  }

  @Override
  String log(String indent) {
    String str = indent + (type == null ? "null " : type.node.caption + " ")
        + node.caption + "\n";
    for(Structure child : children.values()) str += child.log(indent + "  ");
    return str;
  }
  
  public static void error(String message) {
    Base.error("Processing error", message);
  }
}

