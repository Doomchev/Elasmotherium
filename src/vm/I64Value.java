package vm;

public class I64Value extends VMValue {
  public long value;

  public I64Value(long value) {
    this.value = value;
  }

  @Override
  public VMValue create() {
    return new I64Value(0);
  }
  
  @Override
  public long i64Get() {
    return value;
  }
  
  @Override
  public void i64Set(long value) {
    this.value = value;
  }

  @Override
  public void increment() {
    this.value++;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}