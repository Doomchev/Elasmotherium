package base;

import vm.VMBase;
import vm.VMCommand;

public class Log extends StringFunctions {
  public static LineReader currentLineReader;
  public static SymbolReader currentSymbolReader;
  public static StringBuilder subIndent = new StringBuilder();
  public static final boolean log = true;
  
  public String getClassName() {
    return getClass().getSimpleName();
  }

  public static void appendLog(VMCommand command) {
    if(log) println(subIndent + command.toString());
    VMBase.append(command);
  }
  
  public static void error(String title, String message) {
    /*JOptionPane.showMessageDialog(null, message, title
        , JOptionPane.ERROR_MESSAGE);*/
    System.out.println(title);
    System.out.println(message);
    System.exit(1);
  }
  
  public static void println(String string) {
    System.out.println(string);
  }
  
  public static void printChapter(String string) {
    final int length = 70, half = length / 2 - 1;
    System.out.println(createString('=', length));
    System.out.println(createString('=', half - string.length() / 2) + " "
         + string + " " + createString('=', half - (string.length() + 1) / 2));
    System.out.println(createString('=', length));
  }

  @Override
  public String toString() {
    return getClassName();
  }
}
