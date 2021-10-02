package base;

import exception.ElException;
import ast.ClassEntity;
import vm.VMCommand;

public class Conversion extends Scopes {
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
}
