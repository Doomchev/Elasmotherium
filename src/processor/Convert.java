package processor;

import processor.parameter.ProParameter;
import exception.ElException;
import exception.EntityException;

class Convert extends ProCommand {
  static final Convert instance = new Convert(null, null, 0);

  private final ProParameter from, to;

  private Convert(ProParameter from, ProParameter to, int proLine) {
    super(proLine);
    this.from = from;
    this.to = to;
  }

  @Override
  public ProCommand create(String param, int proLine) throws ElException {
    String[] part = trimmedSplit(param, ',');
    return new Convert(ProParameter.get(part[0]), ProParameter.get(part[1])
        , proLine);
  }

  @Override
  public void execute() throws ElException, EntityException {
    convert(from.getNativeClass(), to.getNativeClass());
  }
}