package parser;

public class Error extends Action {
  private final String errorText;

  public Error(String text) {
    this.errorText = text;
  }
  
  public Error derive(String param) {
    return new Error(errorText.replace("\\0", param));
  }

  @Override
  public Action execute() {
    Sub errorActionSub = getErrorActionSub();
    if(errorActionSub == null) actionError(errorText);
    if(log) log("ERROR - RETURNING TO " + errorActionSub.name + "\n"
        + errorActionSub.name);
    return errorActionSub.action;
  }
}
