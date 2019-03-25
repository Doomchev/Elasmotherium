package parser.structure;

public class SubType extends Structure {
  int index;
  ClassScope classScope;
  
  public SubType(Node node, ClassScope parent, int index) {
    super(node, parent);
    this.classScope = parent;
    this.index = index;
  }

  @Override
  public SubType toSubType() {
    return this;
  }

  @Override
  public Structure resolveType(Structure structure) {
    return structure.typeParam[index];
  }  

  @Override
  String typeStr() {
    return node.caption;
  }
}
