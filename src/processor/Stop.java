package processor;

import exception.ElException;

public class Stop extends ProCommand {
  static final Stop instance = new Stop(0);

  private Stop(int proLine) {
    super(proLine);
  }

  @Override
  public ProCommand create(String param, int proLine) throws ElException {
    return new Stop(proLine);
  }
  
  @Override
  public void execute() throws ElException {
    int a = 0;
  }
}
