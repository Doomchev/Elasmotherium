package parser;

public class ActionAdd extends Action {
  private final String string;

  public ActionAdd(String string) {
    this.string = string;
  }

  @Override
  public Action execute() {
    if(tokenStart < textPos) prefix += text.substring(tokenStart, textPos);
    if(log) System.out.println(" ADD " + string + " to " + prefix);
    prefix += string;
    return nextAction;
  }
}
