package ast;

import base.ElException;

public abstract class Value extends Entity {
  @Override
  public void moveToStringSequence(StringSequence sequence) {
    sequence.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) {
    call.add(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.add(this);
  }

  @Override
  public void moveToParameters(Parameters parameters) {
    parameters.add(this);
  }

  @Override
  public void moveToList(ListEntity list) throws ElException {
    list.values.add(this);
  }

  @Override
  public void moveToObjectEntry(ObjectEntry entry) {
    entry.value = this;
  }

  @Override
  public void moveToVariable(Variable variable) {
    variable.setValue(this);
  }
}
