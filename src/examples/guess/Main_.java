package examples.guess;

public class Main_ {
  public static void main(String[] args) {
    int number = (new java.util.Random()).nextInt(100);
    while(true) {
      int guess = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("What number did I guess?"));
      if(guess < number) {
        javax.swing.JOptionPane.showMessageDialog(null, "Your number is too small!");
      } else {
        if(guess > number) {
          javax.swing.JOptionPane.showMessageDialog(null, "Your number is too big!");
        } else {
          javax.swing.JOptionPane.showMessageDialog(null, "You are right!");
          System.exit(0);
        }
      }
    }
  }
}