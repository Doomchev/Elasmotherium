package processor;

import base.ElException;

class Convert extends ProCommand {
  static final Convert instance = new Convert(null, null);

  private final ProParameter from, to;

  private Convert(ProParameter from, ProParameter to) {
    this.from = from;
    this.to = to;
  }

  @Override
  ProCommand create(String param) throws ElException {
    String[] part = trimmedSplit(param, ',');
    return new Convert(ProParameter.get(part[0]), ProParameter.get(part[1]));
  }

  @Override
  void execute() throws ElException {
    convert(from.getType(), to.getType());
  }
}
