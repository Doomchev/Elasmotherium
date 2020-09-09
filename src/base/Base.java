package base;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import parser.structure.Function;
import parser.structure.ID;
import parser.structure.Scope;

public class Base {
  public static int lineNum;
  public static String currentFileName;
  public static final boolean log = true;
  public static String workingPath, modulesPath;
  public static final Function main = new Function(ID.get("main"));
  public static final Scope globalScope = new Scope(null);
  
  public static final String JAVA = "java";
  public static ID constructorID = ID.get("constructor");
  
  static {
    main.code.scope = new Scope(globalScope);
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
