package parser.structure;

import base.Base;
import base.Module;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import parser.Category;
import parser.Rules;

public final class NodeProcessor {
  public class Scope {
    String name;
    Scope parent;
    boolean isFixed, isClass;
    final HashMap<String, Scope> fields = new HashMap<>();
    final LinkedList<Scope> children = new LinkedList<>();
    final HashSet<Scope> links = new HashSet<>();
    final LinkedList<Scope> params = new LinkedList<>();

    Scope get(String name, boolean isFixed, boolean isClass) {
      Scope scope = fields.get(name);
      if(scope == null) {
        scope = new Scope(name, this, isFixed, isClass);
        fields.put(name, scope);
      } else {
        scope.isFixed = isFixed;
      }
      return scope;
    }
    
    Scope get(String name) {
      Scope scope = fields.get(name);
      if(scope == null) {
        scope = new Scope(name, this, false, false);
        fields.put(name, scope);
      }
      return scope;
    }
    
    Scope get(int index) {
      if(params.size() <= index) {
        Scope scope = new Scope("", this, true, false);
        params.add(scope);
        return scope;
      } else {
        return params.get(index);
      }
    }
  
    Scope get(Node node) {
      if(node.type == catVariable) {
        if(node.caption.equals("this")) return parent;
        return get(node.caption);
      } else if(node.type == catDot) {
        return get(node.first()).get(node.last().caption);
      } else if(node.type == catAtIndex) {
        return get(node.first()).get("value");
      } else if(node.type == catValue || node.type == catDefault
          || node.type == catFunctionCall) {
        return get(node.first());
      } else if(node.type == catObject) {
        Scope cl = new Scope("", this, true, true);
        processClass(node, cl, this);
        return cl;
      } else if(node.type == catFunctionAnon) {
        Scope func = new Scope("", this, true, false);
        processFunction(node, func, this, false);
        return func;
      } else {
        return functions.get(node.type).create(node, this);
      }
    }
    
    Scope addNew() {
      Scope scope = new Scope("", this, true, false);
      children.add(scope);
      return scope;
    }
    
    Scope(String name, Scope parent, boolean fixed, boolean isClass) {
      this.name = name;
      this.parent = parent;
      this.isFixed = fixed;
      this.isClass = isClass;
    }

    public Scope() {
      this("", null, true, false);
    }

    public String log() {
      return log("");
    }
    
    String log(String indent) {
      String str = indent + (isFixed ? "#" : "") + name + "\n";
      for(Scope scope : fields.values()) str += scope.log(indent + "  ");
      for(Scope scope : children) str += scope.log(indent + "  ");
      return str;
    }

    Scope create(Node node, Scope scope) {
      return this;
    }
    
    void addLink(Scope scope) {
      if(scope == this) return;
      links.add(scope);
      scope.links.add(this);
    }
    
    void merge() {
      if(!isFixed) {
        Scope parentScope = parent == null ? null : parent.parent;
        while(parentScope != null) {
          Scope scope = parentScope.fields.get(name);
          if(scope != null) {
            for(Scope linkedScope : links) {
              if(linkedScope == this) Base.error("error", "error in links");
              if(scope == this) Base.error("error", "error in links");
              linkedScope.links.remove(this);
              scope.addLink(parentScope);
            }
          }
          parentScope = parentScope.parent;
        }
      }
      for(Scope scope : children) scope.merge();
      for(Scope scope : fields.values()) scope.merge();
    }
  }
  
  class Addition extends Scope {
    Scope scope0 = null, scope1 = null;

    Addition() {
    }
    
    Addition(Node node, Scope scope) {
      this.scope0 = scope.get(node.first());
      this.scope1 = scope.get(node.children.get(1));
    }

    @Override
    Scope create(Node node, Scope scope) {
      return new Addition(node, scope);
    }
  }
  
  class Brackets extends Scope {
    Scope scope;
    
    Brackets() {
    }
    
    Brackets(Node node, Scope scope) {
      this.scope = scope.get(node.first());
    }

    @Override
    Scope create(Node node, Scope scope) {
      return new Brackets(node, scope);
    }
  }
  
  class NumericBinary extends Addition {
    NumericBinary() {
    }
    
    NumericBinary(Node node, Scope scope) {
      super(node, scope);
    }

    @Override
    Scope create(Node node, Scope scope) {
      return new NumericBinary(node, scope);
    }
  }
  
  class BooleanBinary extends Addition {
    BooleanBinary() {
    }
    
    BooleanBinary(Node node, Scope scope) {
      super(node, scope);
    }

    @Override
    Scope create(Node node, Scope scope) {
      return new BooleanBinary(node, scope);
    }
  }
  
  class IfOp extends Addition {
    Scope scope2 = null;
    
    IfOp() {
    }
    
    IfOp(Node node, Scope scope) {
      super(node, scope);
      this.scope2 = scope.get(node.last());
    }

    @Override
    Scope create(Node node, Scope scope) {
      return new IfOp(node, scope);
    }
  }
  
  
  Scope currentScope, rootScope = new Scope();
  
  Scope cClass = rootScope.get("Class", true, true);
  Scope cUnknown = rootScope.get("Unknown", true, true);
  Scope cBoolean = rootScope.get("Boolean", true, true);
  Scope cInteger = rootScope.get("Integer", true, true);
  Scope cString = rootScope.get("String", true, true);
  Scope cTexture = rootScope.get("Texture", true, true);
  Scope cArray = rootScope.get("Array", true, true);
  Scope cList = rootScope.get("List", true, true);
  HashMap<Category, Scope> functions = new HashMap<>();
  Category catClass, catConstructor, catMethod, catThis, catParameter
      , catDefault, catName, catCode, catFunction, catDo, catFor, catForEach
      , catEquate, catDot, catVariable, catField, catFunctionCall, catAtIndex
      , catParameters, catEntry, catInteger, catString, catIncrement, catValue
      , catIf, catIfElse, catCondition, catCodeElse, catInit, catIteration
      , catIfOp, catObject, catFunctionAnon;

  void setFunction(Rules rules, String categories, Scope scope) {
    for(String categoryName : categories.split(" "))
      functions.put(rules.getCategory(categoryName), scope);
  }
  
  public NodeProcessor(Rules rules) {
    catClass = rules.getCategory("class");
    catInteger = rules.getCategory("integer");
    catString = rules.getCategory("string");
    catObject = rules.getCategory("object");
    catFunctionAnon = rules.getCategory("function_anon");

    catConstructor = rules.getCategory("constructor");
    catParameter = rules.getCategory("parameter");
    catParameters = rules.getCategory("parameters");
    catName = rules.getCategory("name");
    catDefault = rules.getCategory("default");
    catCode = rules.getCategory("code");
    catMethod = rules.getCategory("method");
    catFunction = rules.getCategory("function");
    catEntry = rules.getCategory("entry");
    catAtIndex = rules.getCategory("atIndex");
    catValue = rules.getCategory("value");
    catIfOp = rules.getCategory("ifOp");
    
    catIf = rules.getCategory("if");
    catIfElse = rules.getCategory("if_else");
    catCondition = rules.getCategory("condition");
    catCodeElse = rules.getCategory("code_else");
    catDo = rules.getCategory("do");
    catFor = rules.getCategory("for");
    catInit = rules.getCategory("init");
    catIteration = rules.getCategory("iteration");
    catForEach = rules.getCategory("for_each");
    catFunctionCall = rules.getCategory("functionCall");
    catCondition = rules.getCategory("condition");
    catIncrement = rules.getCategory("increment");
    
    catThis = rules.getCategory("this");
    catEquate = rules.getCategory("equate");
    catDot = rules.getCategory("dot");
    catVariable = rules.getCategory("variable");
    catField = rules.getCategory("classField");
    
    setFunction(rules, "integer", cInteger);
    setFunction(rules, "string", cString);
    setFunction(rules, "array", cArray);
    setFunction(rules, "subtraction multiplication division"
        , new NumericBinary());
    setFunction(rules, "more less moreOrEqual lessOrEqual equal notEqual not"
        + " and or", new BooleanBinary());
    setFunction(rules, "addition", new Addition());
    setFunction(rules, "brackets", new Brackets());
    setFunction(rules, "ifOp", new IfOp());
  }
  
  

  private void processFunction(Node node, Scope func, Scope parentScope
      , boolean isConstructor) {
    Node codeNode = node.getChild(catCode);
    for(Node param : node.children) {
      if(param.type == catParameter) {
        Node defaultNode = param.getChild(catDefault);
        if(defaultNode != null) {
          Node thisNode = isConstructor ? param.getChild(catThis) : null;
          if(thisNode == null) {
            func.get(param.caption, true, false)
                .addLink(parentScope.get(defaultNode));
          } else {
            parentScope.get(thisNode.caption, true, false).addLink(parentScope
                .get(defaultNode));
          }
        }
      }
    }
    processCode(codeNode, func);
  }
  
  public Scope processModule(Module module) {
    processCode(module.rootNode, rootScope);
    //rootScope.merge();
    return rootScope;
  }
  
  void processCode(Node node, Scope scope) {
    for(Node childNode : node.children) {
      if(childNode.type == catClass) {
        processClass(childNode, scope.get(childNode.caption, true, true), scope);
      } else if(childNode.type == catFunction) {
        processFunction(childNode, scope.get(childNode.caption), scope, false);
      } else if(childNode.type == catFunctionCall) {
        Scope callScope = scope.get(childNode.first());
        LinkedList<Node> list = childNode.getChild(catParameters).children;
        for(int index = 0; index < list.size(); index++)
          callScope.get(index).addLink(scope.get(list.get(index)));
      } else if(childNode.type == catEquate) {
        scope.get(childNode.first()).addLink(scope.get(childNode.last()));
      } else if(childNode.type == catIncrement) {
        scope.get(childNode.first()).addLink(cInteger);
      } else if(childNode.type == catIf) {
        processCode(childNode.getChild(catCondition), scope);
        processCode(childNode.getChild(catCode), scope);
      } else if(childNode.type == catIfElse) {
        processCode(childNode.getChild(catCondition), scope);
        processCode(childNode.getChild(catCode), scope);
        processCode(childNode.getChild(catCodeElse), scope);
      } else if(childNode.type == catFor) {
        processCode(childNode.getChild(catInit), scope);
        processCode(childNode.getChild(catCondition), scope);
        processCode(childNode.getChild(catIteration), scope);
        processCode(childNode.getChild(catCode), scope);
      } else if(childNode.type == catForEach) {
        processCode(childNode.getChild(catCode), scope);
      }
    }
  }

  void processClass(Node node, Scope cl, Scope parentScope) {
    for(Node childNode : node.children) {
      if(childNode.type == catConstructor) {
        processFunction(childNode, cl.get(childNode.caption, true, false), cl, true);
      } else if(childNode.type == catMethod) {
        processFunction(childNode, cl.get(childNode.caption, true, false), cl, false);
      } else if(childNode.type == catEntry) {
        cl.get(childNode.first().caption, true, false)
            .addLink(cl.get(childNode.last()));
      }
    }
  }
}