package examples.factorial;

public class Main_ {
  public static void main(String[] args) {
    System.out.println("Factorial of 5 is " + factorial(5) + ".");
  }
  static int factorial(int num) {
    return num == 1 ? 1 : factorial(num - 1) * num;
  }
}