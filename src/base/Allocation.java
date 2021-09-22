package base;

import ast.function.CustomFunction;
import java.util.LinkedList;

public class Allocation extends Conversion {
  
  // variables

  private static final LinkedList<Integer> allocations = new LinkedList<>();
  public static int currentAllocation;
  
  public static void allocate() {
    allocations.add(currentAllocation);
  }
  
  public static int deallocate() {
    currentFunction.setAllocation();
    int value = currentAllocation;
    currentAllocation = allocations.getLast();
    allocations.removeLast();
    return value;
  }
  
  // functions
  
  public static CustomFunction currentFunction;
  
  public static CustomFunction allocateFunction(CustomFunction function) {
    allocate();
    currentAllocation = 0;
    return function;
  }
  
  public static int deallocateFunction() {
    return deallocate();
  }
}
