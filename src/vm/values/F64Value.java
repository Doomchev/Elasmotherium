package vm.values;

public class F64Value extends VMValue {
  private double value;

  public F64Value(double value) {
    this.value = value;
  }

  @Override
  public VMValue create() {
    return new F64Value(0);
  }
  
  @Override
  public double f64Get() {
    return value;
  }
  
  @Override
  public void f64Set(double value) {
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
