package base;

import ast.exception.ElException;
import java.util.LinkedList;
import java.util.List;

public class StringFunctions {
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
    if(!string.endsWith(end)) throw new ElException.MethodException("Base"
        , "expectEnd", end + " expected");
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
    if(!str.endsWith("\"") || str.length() < 2) throw new ElException.MethodException(
        "Base", "stringParam", "Invalid token");
    return str.substring(1, str.length() - 1);
  }
  
  public static String removeLastDigit(String string) {
    int lastIndex = string.length() - 1;
    char lastSymbol = string.charAt(lastIndex);
    if(Character.isDigit(lastSymbol)) return string.substring(0, lastIndex);
    return string;
  }
}
