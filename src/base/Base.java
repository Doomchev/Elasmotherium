package base;

import ast.ClassEntity;
import ast.ID;
import ast.NamedEntity;
import ast.function.CustomFunction;
import base.ElException.MethodException;
import base.ElException.NotFound;
import java.util.LinkedList;
import java.util.List;
import processor.Processor;
import vm.VMBase;
import vm.VMCommand;

public abstract class Base {
  public static int currentLineNum = 0;
  public static String currentFileName;
  public static StringBuilder subIndent = new StringBuilder();
  public static final boolean log = true;
  public static String workingPath, modulesPath;
  public static Processor currentProcessor;
  public static ClassEntity currentClass;
  
  static {
    try {
      workingPath = new java.io.File(".").getCanonicalPath();
      modulesPath = new java.io.File(".").getCanonicalPath() + "/modules";
    } catch (java.io.IOException ex) {
    }
  }
  
  public String getClassName() {
    return getClass().getSimpleName();
  }
  
  // variables

  private static final LinkedList<Integer> allocations = new LinkedList<>();
  public static int currentAllocation;
  
  public static void allocate() {
    allocations.add(currentAllocation);
  }
  
  public static int deallocate() {
    currentFunction.setAllocation();
    int value = currentAllocation;
    currentAllocation = allocations.getLast();
    allocations.removeLast();
    return value;
  }
  
  // functions
  
  //private static final LinkedList<CustomFunction> functions
  //    = new LinkedList<>();
  public static CustomFunction currentFunction;
  
  public static CustomFunction allocateFunction(CustomFunction function) {
    allocate();
    currentAllocation = 0;
    //functions.add(currentFunction);
    //currentFunction = function;
    return function;
  }
  
  public static int deallocateFunction() {
    //currentFunction = functions.getLast();
    //functions.removeLast();
    return deallocate();
  }
  
  // scopes
  
  private static final NamedEntity[] scope = new NamedEntity[64];
  private static final LinkedList<Integer> scopeEnd = new LinkedList<>();
  private static int lastScopeEntry = -1;
      
  public static void allocateScope() {
    scopeEnd.add(lastScopeEntry);
  }
  
  public void deallocateScope() {
    lastScopeEntry = scopeEnd.getLast();
    scopeEnd.removeLast();
  }
  
  public void addToScope(NamedEntity entity) {
    lastScopeEntry++;
    scope[lastScopeEntry] = entity;
  }
  
  public NamedEntity getFunctionFromScope(ID name, int parametersQuantity)
      throws ElException {
    for(int i = lastScopeEntry; i >= 0; i--)
      if(scope[i].isFunction(name, parametersQuantity)) return scope[i];
    throw new NotFound("getFunctionFromScope", name.string);
  }
  
  public NamedEntity getVariableFromScope(ID name)
      throws ElException {
    for(int i = lastScopeEntry; i >= 0; i--)
      if(scope[i].isVariable(name)) return scope[i];
    throw new NotFound("getVariableFromScope", name.string);
  }
  
  public ClassEntity getClassFromScope(ID name) throws ElException {
    for(int i = lastScopeEntry; i >= 0; i--) {
      NamedEntity entity = scope[i];
      if(entity instanceof ClassEntity && entity.isVariable(name))
        return (ClassEntity) entity;
    }
    throw new NotFound("getClassFromScope", "Class " + name);
  }
  
  public void printScope() {
    StringBuilder string = new StringBuilder();
    for(int i = 0; i <= lastScopeEntry; i++) {
      if(i > 0) string.append(", ");
      string.append(scope[i].getName());
    }
    println(string.toString());
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
    list.add(text.substring(start).trim());
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
    int index = string.indexOf(c);
    return index < 0 ? string : string.substring(0, index);
  }
  
  public static String expectEnd(String string, String end) throws ElException {
    if(!string.endsWith(end)) throw new MethodException("Base", "expectEnd"
        , end + " expected");
    return string.substring(0, string.length() - end.length());
  }
  
  public static String createString(char ch, int quantity) {
    char[] chars = new char[quantity];
    for(int i = 0; i < quantity; i++) chars[i] = ch;
    return new String(chars);
  }
  
  public static String decapitalize(String string) {
    return string.substring(0, 1).toLowerCase() + string.substring(1);
  }
  
  public static String listToString(List<? extends Object> list) {
    return listToString(list, ", ");
  }
  
  public static String listToString(List<? extends Object> list
      , String delimiter) {
    StringBuilder str = new StringBuilder();
    for(Object object : list) {
      if(str.length() > 0) str.append(delimiter);
      str.append(object.toString());
    }
    return str.toString();
  }
  
  public static LinkedList<String> listSplit(String commands, char delimiter) {
    LinkedList<String> commandList = new LinkedList<>();
    int start = -1, brackets = 0;
    boolean quotes = false;
    for(int i = 0; i < commands.length(); i++) {
      char c = commands.charAt(i);
      if(brackets == 0 && start < 0 && c != delimiter) start = i;
      if(c == '"') quotes = !quotes;
      if(quotes) continue;
      if(c == '(') {
        brackets++;
      } else if(c == ')') {
        brackets--;
      } else if(c == delimiter) {
        if(brackets > 0 || start < 0) continue;
        commandList.add(commands.substring(start, i).trim());
        start = -1;
      }
    }
    if(start >= 0) commandList.add(commands.substring(start).trim());
    //System.out.println(commands + " = " + listToString(commandList));
    return commandList;
  }

  public static String stringParam(String str) throws ElException {
    if(!str.endsWith("\"") || str.length() < 2) throw new MethodException(
        "Base", "stringParam", "Invalid token");
    return str.substring(1, str.length() - 1);
  }
  
  public static String removeLastDigit(String string) {
    int lastIndex = string.length() - 1;
    char lastSymbol = string.charAt(lastIndex);
    if(Character.isDigit(lastSymbol)) return string.substring(0, lastIndex);
    return string;
  }
  
  // reader
  
  public static class EReader {
    private java.io.BufferedReader reader;
    private String fileName;

    public EReader(String fileName) {
      this.fileName = fileName;
      try {
        reader = new java.io.BufferedReader(new java.io.FileReader(fileName));
      } catch (java.io.FileNotFoundException ex) {
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
      } catch (java.io.IOException ex) {
        error("I/O error", "Cannot read " + fileName + ".");
      }
      return null;
    }
  }
  
  // converters
  
  private static final LinkedMap<ClassEntity, LinkedMap<ClassEntity
      , VMCommand>> converters = new LinkedMap<>();
  
  static {
    add(ClassEntity.Int, ClassEntity.String, new vm.convert.I64ToString());
    add(ClassEntity.Int, ClassEntity.Float, new vm.convert.I64ToF64());
  }
  
  private static void add(ClassEntity from, ClassEntity to, VMCommand command) {
    LinkedMap<ClassEntity, VMCommand> map = converters.get(from);
    if(map == null) {
      map = new LinkedMap<>();
      converters.put(from, map);
    }
    map.put(to, command);
  }

  public static void convert(ClassEntity from, ClassEntity to)
      throws ElException {
    if(from == to) return;
    if(log) System.out.println(subIndent + "converting " + from + " to "
        + to + ".");
    LinkedMap<ClassEntity, VMCommand> map = converters.get(from);
    if(map == null)
      throw new ElException(from, "Converters from " + from
          + " are not found.");
    VMCommand command = map.get(to);
    if(command == null) {
      throw new ElException(from, "Converters from " + from + " to " + to
          + " are not found.");
    }
    appendLog(command.create());
  }
  
  // other

  public static void appendLog(VMCommand command) {
    if(log) println(subIndent + command.toString());
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