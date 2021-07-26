package base;

import ast.Entity;
import java.io.File;
import java.io.IOException;
import ast.ID;
import ast.Variable;
import java.util.LinkedList;
import processor.Processor;

public class Base {
  public static int lineNum;
  public static String currentFileName;
  public static final boolean log = true;
  public static String workingPath, modulesPath;
  public static Processor currentProcessor;
  
  public static final String JAVA = "java";
  public static ID constructorID = ID.get("constructor");
  
  static {
    try {
      workingPath = new File(".").getCanonicalPath() + "/";
      modulesPath = new File(".").getCanonicalPath() + "/modules/";
    } catch (IOException ex) {
    }
  }
  
  public static String[] trimmedSplit(String text, char separator) {
    int start = 0;
    LinkedList<String> list = new LinkedList<>();
    for(int i = 0; i < text.length(); i++) {
      if(text.charAt(i) == separator) {
        list.add(text.substring(start, i).trim());
        start = i + 1;
      }
    }
    list.add(text.substring(start));
    return list.toArray(new String[list.size()]);
  }
  
  public static String betweenBrackets(String text, char opening
      , char closing) {
    int start = -1;
    for(int i = 0; i < text.length(); i++) {
      if(text.charAt(i) == opening) start = i;
      if(text.charAt(i) == closing) return i < 0 ? "" : text.substring(
          start + 1, i);
    }
    return "";
  }
  
  public static String startingId(String text) {
    for(int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if(c >= 'A' && c <= 'Z') continue;
      if(c >= 'a' && c <= 'z') continue;
      return text.substring(0, i);
    }
    return text;
  }
  
  
  private static class ScopeMap extends SimpleMap<String, Variable> {};
  
  private static final int maxScope = 255;
  private static final ScopeMap[] scopes = new ScopeMap[maxScope];
  private static int lastScope = -1;
  
  public void pushScope() {
    lastScope++;
  }
  
  public void popScope() {
    scopes[lastScope] = null;
    lastScope--;
  }
  
  public void putVariable(Variable variable) {
    if(scopes[lastScope] == null) scopes[lastScope] = new ScopeMap();
    scopes[lastScope].put(variable.name.string, variable);
  }
  
  public Variable getVariable(String name) {
    for(int i = lastScope; i >= 0; i--) {
      if(scopes[i] == null) continue;
      Variable var = scopes[i].get(name);
      if(var != null) return var;
    }
    return null;
  }
  
  
  
  public static void error(String title, String message) {
    /*JOptionPane.showMessageDialog(null, message, title
        , JOptionPane.ERROR_MESSAGE);*/
    System.out.println(title + "\n" + message);
    System.exit(1);
  }
  
  public static void println(String string) {
    System.out.println(string);
  }
}
