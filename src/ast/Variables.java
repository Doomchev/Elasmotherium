package ast;

import java.util.LinkedList;

public class Variables {
  public Variables parent;
  public Code code;
  public final LinkedList<Variable> list = new LinkedList<>();

  public Variables(Variables parent, Code code) {
    this.parent = parent;
    this.code = code;
  }
  
  public Variable get(ID name) {
    Variables variables = this;
    while(variables != null) {
      for(Variable variable : variables.list)
        if(variable.name == name) return variable;
      variables = variables.parent;
    }
    throw new Error("Cannot find variable \"" + name.string + "\"");
  }
  
  public Function getFunction(ID name) {
    Variables variables = this;
    while(variables != null) {
      if(variables.code != null)
        for(Function function : variables.code.functions)
          if(function.name == name) return function;
      variables = variables.parent;
    }
    throw new Error("Cannot find function \"" + name.string + "\"");
  }
}
