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
    parsingError(errorText);
    return null;
  }
}
