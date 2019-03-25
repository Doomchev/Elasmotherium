package parser.structure;

public class Function {
  Structure type;

  public Function() {
  }

  public Function(Structure type) {
    this.type = type;
  }

  Structure calcType(Node node, Scope scope) {
    return type;
  }
}
