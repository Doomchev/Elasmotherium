package vm;

import base.Base;
import java.util.HashMap;
import java.util.LinkedList;
import parser.structure.Function;

public class VMBase {
  public static final int STACK_SIZE = 2 << 10;
  public static boolean[] booleanStack = new boolean[STACK_SIZE];
  public static long[] i64Stack = new long[STACK_SIZE];
  public static String[] stringStack = new String[STACK_SIZE];
  public static VMFunctionCall[] callStack = new VMFunctionCall[STACK_SIZE];
  public static int booleanStackPointer = -1, i64StackPointer = -1
      , stringStackPointer = -1, callStackPointer = -1;
  public static VMFunctionCall currentCall = new VMFunctionCall();
  public static Command currentCommand, startingCommand;
  public static final HashMap<Function, Command> functions = new HashMap<>();
  public static final LinkedList<Command> gotos = new LinkedList<>();
  public static Command[] commands = new Command[STACK_SIZE];
  public static int commandNumber = -1;
  
  public static final int BOOLEAN_STACK = 0, INT_STACK = 1, STRING_STACK = 2
      , STACK_QUANTITY = 3;
  

  public static void prepare() {
    Base.main.toByteCode();
    for(int index = 0; index <= commandNumber; index++)
      System.out.println(commands[index].toString());
    Command command = startingCommand;
    while(command != null) {
      System.out.println(command.toString());
      command = command.execute();
      
      String stack = "";
      for(int index = 0; index <= i64StackPointer; index++)
        stack += i64Stack[index] + " ";
      System.out.println("i64: " + stack);
      
      stack = "";
      for(int index = 0; index <= stringStackPointer; index++)
        stack += stringStack[index] + " ";
      System.out.println("string: " + stack);
    }
  }
}
