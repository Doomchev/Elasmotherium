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
import processor.ProBase;
import processor.Processor;
import vm.I64ToString;
import vm.VMBase;
import vm.VMCommand;

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

  // string functios
  
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
  
  public static String between(String text, char opening
      , char closing) {
    int start = -1;
    for(int i = 0; i < text.length(); i++) {
      if(text.charAt(i) == opening) start = i;
      if(text.charAt(i) == closing) return i < 0 ? "" : text.substring(
          start + 1, i);
    }
    return "";
  }
  
  public static String betweenBrackets(String text) {
    return between(text, '(', ')');
  } 
  
  public static String stringUntil(String string, char c) {
    return string.substring(0, string.indexOf(c));
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
  
  public static String createString(char ch, int quantity) {
    char[] chars = new char[quantity];
    for(int i = 0; i < quantity; i++) chars[i] = ch;
    return new String(chars);
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
  
  // converters
  
  private static final SimpleMap<ClassEntity, SimpleMap<ClassEntity
      , VMCommand>> converters = new SimpleMap<>();
  static ClassEntity returnType = null;
  
  static {
    add(ClassEntity.Int, ClassEntity.String, new I64ToString());
  }
  
  private static void add(ClassEntity from, ClassEntity to, VMCommand command) {
    SimpleMap<ClassEntity, VMCommand> map = converters.get(from);
    if(map == null) {
      map = new SimpleMap<>();
      converters.put(from, map);
    }
    map.put(to, command);
  }

  public void setReturnType(Entity type) throws ElException {
    if(type == null) return;
    if(returnType != null) 
      throw new ElException("Return type already specified.");
    returnType = type.getType();
    if(log) System.out.println(subIndent + "Set return type to "
        + returnType);
  }

  public static void convert(ClassEntity to) throws ElException {
    if(returnType == null) {
      throw new ElException("Missing return.");
    }
    convert(returnType, to);
    returnType = null;
  }

  public static void convert(ClassEntity from, ClassEntity to) throws ElException {
    if(log) System.out.println(subIndent + "Converting " + returnType + " to "
        + to + ".");
    if(from == to) {
      return;
    }
    SimpleMap<ClassEntity, VMCommand> map = converters.get(from);
    if(map == null) {
      throw new ElException("Converters from " + from + " are not found.");
    }
    VMCommand command = map.get(to);
    if(command == null) {
      throw new ElException("Converters from " + from + " to " + to + " are not found.");
    }
    append(command.create(null));
  }
  
  // other

  public static void append(VMCommand command) {
    VMBase.append(command);
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
  
  public static void printChapter(String string) {
    System.out.println(createString('=', 80));
    System.out.println(createString('=', 39 - string.length() / 2) + " "
         + string + " " + createString('=', 39 - (string.length() + 1) / 2));
    System.out.println(createString('=', 80));
  }
}
