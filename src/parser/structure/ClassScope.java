package parser.structure;

public class ClassScope extends Scope {
  ClassScope parentClass;

  ClassScope(Node node, Scope parent, ClassScope parentClass) {
    super(node, parent);
    this.parentClass = parentClass;
  }
  
  ClassScope() {
    this(null, null, null);
  }

  @Override
  public ClassScope toClass() {
    return this;
  }
  @Override
  public ClassScope resolve() {
    return this;
  }

  Scope findClass() {
    Scope scope = this;
    while(scope != null) {
      ClassScope classScope = scope.toClass();
      if(classScope != null) return classScope;
      scope = scope.parent;
    }
    return null;
  }

  Structure find(String name, boolean isField) {
    return isField ? findField(name) : findVariable(name);
  }

  @Override
  Structure findType(String name) {
    Structure field = children.get(name);
    if(field != null) return field;
    return parent.findField(name);
  }

  @Override
  Structure findField(String name) {
    Structure field = children.get(name);
    if(field != null) return field;
    if(parentClass == null) error("Type " + name + " is not found.");
    return parentClass.findField(name);
  }

  @Override
  Structure findVariable(String name) {
    if(parentClass == null) {
      Structure var = children.get(name);
      if(var != null) return var;
      if(parent == null) return null;
    }
    return parent.findVariable(name);
  }

  @Override
  boolean isChildOf(ClassScope classScope) {
    ClassScope scope = this;
    while(scope != null) {
      if(classScope == scope) return true;
      scope = scope.parentClass;
    }
    return false;
  }

  @Override
  String log(String indent) {
    String str = indent + "class " + node.caption + "\n";
    for(Structure child : children.values()) str += child.log(indent + "  ");
    return str;
  }
  
  @Override
  String typeStr() {
    return node.caption;
  }

  @Override
  public String toString() {
    return node.caption;
  }

  @Override
  String toPath() {
    return node.caption;
  }
}
