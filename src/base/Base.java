package base;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import ast.Function;
import ast.ID;

public class Base {
  public static int lineNum;
  public static String currentFileName;
  public static final boolean log = false;
  public static String workingPath, modulesPath;
  public static final Function main = new Function(ID.get("main"));
  
  public static final String JAVA = "java";
  public static ID constructorID = ID.get("constructor");
  
  static {
    try {
      workingPath = new File(".").getCanonicalPath() + "/";
      modulesPath = new File(".").getCanonicalPath() + "/modules/";
    } catch (IOException ex) {
    }
  }

  public static void error(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title
        , JOptionPane.ERROR_MESSAGE);
    throw new RuntimeException();
  }
}
