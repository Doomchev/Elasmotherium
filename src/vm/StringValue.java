package vm;

public class StringValue extends VMValue {
  public String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public VMValue create() {
    return new StringValue(null);
  }
  
  @Override
  public String stringGet() {
    return value;
  }
  
  @Override
  public void stringSet(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "\"" + value + "\"";
  }
}
