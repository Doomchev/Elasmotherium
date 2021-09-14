package processor;

import base.ElException;

public class Stop extends ProCommand {
  static final Stop instance = new Stop();

  private Stop() {
  }

  @Override
  public ProCommand create(String param) throws ElException {
    return new Stop();
  }
  
  @Override
  public void execute() throws ElException {
    int a = 0;
  }
}
