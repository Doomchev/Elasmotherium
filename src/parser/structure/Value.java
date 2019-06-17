package parser.structure;

public abstract class Value extends Entity {
  @Override
  void moveToStringSequence(StringSequence sequence) {
    sequence.chunks.add(this);
  }

  @Override
  void moveToFunctionCall(FunctionCall call) {
    call.parameters.add(this);
  }

  @Override
  void moveToFormula(Formula formula) {
    formula.chunks.add(this);
  }

  @Override
  void moveToParameters(Parameters parameters) {
    parameters.parameters.add(this);
  }

  @Override
  void moveToObjectEntry(ObjectEntry entry) {
    entry.value = this;
  }

  @Override
  void moveToVariable(Variable variable) {
    variable.value = this;
  }
}
