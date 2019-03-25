package base;

import javax.swing.JOptionPane;

public class Base {
  public static int lineNum;
  public static String currentFileName;
  public static final boolean log = true;

  public static void error(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    throw new RuntimeException();
  }
}
