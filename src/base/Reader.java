package base;

public abstract class Reader extends Base {
  protected String fileName;
  protected int lineNum;
  
  public abstract String getError();
}
