package processor;

import base.ElException;

public class Stop extends ProCommand {
  public static final Stop instance = new Stop();

  private Stop() {
  }

  @Override
  ProCommand create(String param) throws ElException {
    return new Stop();
  }
  
  @Override
  void execute() throws ElException {
    int a = 0;
  }
}
