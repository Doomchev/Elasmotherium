package parser;

public abstract class Action extends Base {
  public static int savedTextPos, savedLineNum, savedLineStart;
  
  public Action nextAction;
  public abstract Action execute();

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
