package parser.structure;

import java.util.LinkedList;
import parser.Action;

public abstract class FlagEntity extends NamedEntity {
  public final LinkedList<ID> flags = new LinkedList<>();
  
  public final void addFlags() {
    flags.addAll(Action.currentFlags);
    Action.currentFlags.clear();
  }

  @Override
  public boolean hasChild(ID id) {
    for(ID flag : flags) if(id == flag) return true;
    return getChild(id) != null;
  }

  @Override
  public boolean hasFlag(ID id) {
    return flags.contains(id);
  }
}
