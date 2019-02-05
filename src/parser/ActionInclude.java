package parser;

import base.Module;
import static parser.ParserBase.currentParserScope;

public class ActionInclude extends Action {
  int index;

  public ActionInclude(int index) {
    this.index = index;
  }
  
  @Override
  public Action execute() {
    includes.push(new Include());
    Module.include(path + currentParserScope.variables[index].caption);
    if(log) log("INCLUDE " + currentFileName);
    return nextAction;
  }
}
