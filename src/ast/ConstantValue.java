package ast;

public class ConstantValue extends StringValue {
  public ID type;
  
  public ConstantValue(ID type, String value) {
    super(value);
    this.type = type;
  }
  
  @Override
  public ID getID() {
    return constID;
  }  

  @Override
  public String toString() {
    return type + "(" + value + ")";
  }
}
