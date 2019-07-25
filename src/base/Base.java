package base;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import parser.structure.Code;
import parser.structure.Scope;

public class Base {
  public static int lineNum;
  public static String currentFileName;
  public static final boolean log = true;
  public static String workingPath, modulesPath;
  public static Code main = new Code();
  
  public static final String JAVA = "java";
  
  static {
    main.scope = new Scope(null);
    try {
      workingPath = new File(".").getCanonicalPath() + "/";
      modulesPath = new File(".").getCanonicalPath() + "/src/modules/";
    } catch (IOException ex) {
    }
  }

  public static void error(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    throw new RuntimeException();
  }
}
