package processor;

import ast.ClassEntity;
import base.ElException;
import base.SimpleMap;
import vm.*;

public class ProConvert extends ProCommand {
  private static final SimpleMap<ClassEntity, SimpleMap<ClassEntity, VMCommand>>
      converters = new SimpleMap<>();
  
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
  
  public static void convert(ClassEntity from, ClassEntity to)
      throws ElException {
    if(from == to) return;
    SimpleMap<ClassEntity, VMCommand> map = converters.get(from);
    if(map == null) throw new ElException("Converters from " + from
        + " are not found.");
    VMCommand command = map.get(to);
    if(command == null) throw new ElException("Converters from " + from
        + " are not found.");
    VMBase.append(command.create(current));
  }
  
  @Override
  void execute() throws ElException {
    convert(current.getType(), parent.getType());
  }
}
