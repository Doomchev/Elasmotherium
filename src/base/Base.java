package base;

import ast.ClassEntity;
import ast.Entity;
import ast.Function;
import java.io.File;
import java.io.IOException;
import ast.ID;
import ast.NamedEntity;
import base.SimpleMap.Entry;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import processor.Processor;

public abstract class Base {
  public static int currentLineNum = 0;
  public static String currentFileName, subIndent = "";
  public static final boolean log = true;
  public static String workingPath, modulesPath;
  public static Processor currentProcessor;
  public static ClassEntity currentClass;
  
  static {
    try {
      workingPath = new File(".").getCanonicalPath() + "/";
      modulesPath = new File(".").getCanonicalPath() + "/modules/";
    } catch (IOException ex) {
    }
  }
  
  public String getName() {
    return getClass().getSimpleName();
  }
  
  // variables

  private static final LinkedList<Integer> allocations = new LinkedList<>();
  public static int currentAllocation;
  
  public static void allocate() {
    allocations.add(currentAllocation);
  }
  
  public static int deallocate() {
    currentFunction.allocation = Math.max(currentFunction.allocation
        , currentAllocation);
    int value = currentAllocation;
    currentAllocation = allocations.getLast();
    allocations.removeLast();
    return value;
  }
  
  // functions
  
  private static final LinkedList<Function> functions = new LinkedList<>();
  public static Function currentFunction;
  
  public static Function allocateFunction(Function function) {
    allocate();
    currentAllocation = 0;
    functions.add(currentFunction);
    currentFunction = function;
    return function;
  }
  
  public static int deallocateFunction() {
    currentFunction = functions.getLast();
    functions.removeLast();
    return deallocate();
  }
  
  // scopes
  
  private static final ScopeEntry[] scope = new ScopeEntry[1000];
  private static final LinkedList<Integer> scopeEnd = new LinkedList<>();
  private static int lastScopeEntry = -1;

  private static class ScopeEntry extends Entry<ID, Entity> {
    public ScopeEntry(ID key, Entity value) {
      super(key, value);
    }
  }
      
  public static void allocateScope() {
    scopeEnd.add(lastScopeEntry);
  }
  
  public void deallocateScope() {
    lastScopeEntry = scopeEnd.getLast();
    scopeEnd.removeLast();
  }
  
  public void addToScope(ID name, Entity entity) {
    lastScopeEntry++;
    scope[lastScopeEntry] = new ScopeEntry(name, entity);
  }
  
  public void addToScope(NamedEntity entity) {
    addToScope(entity.name, entity);
  }
  
  public Entity getFromScope(ID name) throws ElException {
    for(int i = lastScopeEntry; i >= 0; i--)
      if(scope[i].key == name) return scope[i].value;
    throw new ElException("Identifier " + name + " is not found.");
  }

  // string commands
  
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
  
  public static String[] trimmedSplit(String text, char... separator) {
    int start = 0, separatorIndex = 0;
    LinkedList<String> list = new LinkedList<>();
    for(int i = 0; i < text.length(); i++) {
      if(separatorIndex < separator.length
          && text.charAt(i) == separator[separatorIndex]) {
        list.add(text.substring(start, i).trim());
        separatorIndex++;
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
  
  public static String expectEnd(String string, String end) throws ElException {
    if(!string.endsWith(end)) throw new ElException(end + " expected.");
    return string.substring(0, string.length() - end.length());
  }
  
  //reader
  
  public static class EReader {
    private BufferedReader reader;
    private String fileName;

    public EReader(String fileName) {
      this.fileName = fileName;
      try {
        reader = new BufferedReader(new FileReader(fileName));
      } catch (FileNotFoundException ex) {
        error("I/O error", fileName + " not found.");
      }
      currentLineNum = 0;
    }
    
    public String readLine() {
      try {
        while(true) {
          String line = reader.readLine();
          if(line == null) return null;
          currentLineNum++;
          line = line.trim();
          if(line.isEmpty() || line.startsWith("//")) continue;
          return line;
        }
      } catch (IOException ex) {
        error("I/O error", "Cannot read " + fileName + ".");
      }
      return null;
    }
  }
  
  // other
  
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
