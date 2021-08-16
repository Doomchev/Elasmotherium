package ast;

import base.ElException;

public abstract class Value extends Entity {
  
  @Override
  public void moveToStringSequence(StringSequence sequence) {
    sequence.chunks.add(this);
  }

  @Override
  public void moveToFunctionCall(FunctionCall call) {
    call.parameters.add(this);
  }

  @Override
  public void moveToFormula(Formula formula) {
    formula.chunks.add(this);
  }

  @Override
  public void moveToParameters(Parameters parameters) {
    parameters.parameters.add(this);
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
    variable.value = this;
  }
}
