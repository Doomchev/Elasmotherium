package parser.structure;

import base.Base;
import base.Module;
import java.util.HashMap;
import java.util.LinkedList;
import parser.Category;
import parser.Rules;

public final class NodeProcessor {
  public static class NodeScope {
    Node node;
    Scope scope;

    public NodeScope(Node node, Scope Scope) {
      this.node = node;
      this.scope = Scope;
    }
  }
  
  public class Scope {
    String name;
    Scope parent, parentClass, type = null, valueScope;
    final HashMap<String, Scope> children = new HashMap<>();
    final LinkedList<Node> nodes = new LinkedList<>();
    final LinkedList<Scope> params = new LinkedList<>();
    Node initNode = null, valueNode;

    Scope get(String name, Scope parentClass) {
      Scope scope = children.get(name);
      if(scope == null) {
        scope = new Scope(name, this, parentClass);
        children.put(name, scope);
      }
      return scope;
    }
    
    Scope get(String name) {
      return get(name, null);
    }
    
    Scope get(int index) {
      if(params.size() <= index) {
        Scope scope = new Scope("", this, null);
        params.add(scope);
        return scope;
      } else {
        return params.get(index);
      }
    }
    
    Scope get(Node node) {
      if(node.type == catVariable) {
        if(node.hasChild(catThis)) {
          Scope classScope = findClass();
          if(classScope == null) error("This or $ outside class");
          Scope fieldScope = classScope.children.get(node.caption);
          if(fieldScope == null) error("Field " + node.caption
              + " is not found in " + classScope.name);
          return fieldScope;
        } else {
          Scope varScope = get(node.caption);
          if(varScope == null) error("Variable " + node.caption
              + " is not found");
          return varScope;
        }
      } else if(node.type == catDot) {
        
      }
      error(node.type.name + " is not supported");
      return null;
    }
    
    Scope findClass() {
      Scope scope = this;
      while(scope != null) {
        if(scope.parentClass != null) return scope;
        scope = scope.parent;
      }
      return null;
    }
    
    Scope find(String name, boolean isField) {
      return isField ? findField(name) : findVariable(name);
    }
    
    Scope findField(String name) {
      if(parentClass != null) {
        Scope field = children.get(name);
        if(field != null) return field;
        return parentClass.findField(name);
      } else {
        if(parent == null) return null;
        return parent.findField(name);
      }
    }
    
    Scope findVariable(String name) {
      if(parentClass == null) {
        Scope scope = children.get(name);
        if(scope != null) return scope;
        if(parent == null) return null;
      }
      return parent.findVariable(name);
    }
    
    Scope(String name, Scope parent, Scope parentClass) {
      this.name = name;
      this.parent = parent;
      this.parentClass = parentClass;
    }

    Scope(Scope parent) {
      this("", parent, null);
    }
    
    public Scope() {
      this("", null, null);
    }
    
    void setInit(Node initNode, Node valueNode, Scope valueScope) {
      this.initNode = initNode;
      this.valueNode = valueNode;
      this.valueScope = valueScope;
    }

    public String log() {
      return log("");
    }
    
    String log(String indent) {
      String str = indent + (parentClass != null ? "class " : (type == null ? ""
          : type.name + " ")) + name + "\n";
      for(Scope scope : children.values()) str += scope.log(indent + "  ");
      if(initNode != null) str += indent + "  = " + initNode.last().toString()
          + "\n";
      return str;
    }

    Scope create(Node node, Scope scope) {
      return this;
    }
    
    void setTypes() {
      if(type != null) return;
      if(initNode == null) {
        for(Scope scope : children.values()) if(scope.type == null) 
          scope.setTypes();
      } else {
        type = valueScope.getType(valueNode);
        initNode.getChild(catType).caption = type.name;
      }
    }
    
    Scope getType(Node node) {
      Function func = functions.get(node.type);
      if(func == null) error(node.type.name + " is not supported.");
      return func.getType(node, this);
    }
  }
  
  static class Function {
    Scope type;

    public Function() {
    }

    public Function(Scope type) {
      this.type = type;
    }
    
    Scope getType(Node node, Scope scope) {
      return type;
    }
  }
  
  Scope newClass(String name, Scope parent) {
    Scope scope = new Scope(name, rootScope, parent);
    rootScope.children.put(name, scope);
    return scope;
  }
  
  Scope currentScope, rootScope = new Scope();
  
  Scope cClass = newClass("Class", rootScope);
  Scope cBoolean = newClass("Boolean", cClass);
  Scope cConcatenable = newClass("Concatenable", cClass);
  Scope cNumber = newClass("Integer", cConcatenable);
  Scope cInteger = newClass("Integer", cNumber);
  Scope cFloat = newClass("Float", cNumber);
  Scope cString = newClass("String", cConcatenable);
  HashMap<Category, Function> functions = new HashMap<>();
  Category catClass, catConstructor, catMethod, catThis, catParameter, catReturn
      , catDefault, catName, catCode, catFunction, catDo, catFor, catForEach
      , catEquate, catDot, catVariable, catField, catFunctionCall, catAtIndex
      , catParameters, catInteger, catString, catIncrement, catValue, catType
      , catIf, catCondition, catElse, catInit, catIteration, catStatic, catNew
      , catIfOp, catObject, catGlobal;

  void setFunction(Rules rules, String categories, Function func) {
    for(String categoryName : categories.split(" "))
      functions.put(rules.getCategory(categoryName), func);
  }
  
  public NodeProcessor(Rules rules) {
    catClass = rules.getCategory("class");
    catInteger = rules.getCategory("integer");
    catString = rules.getCategory("string");
    catObject = rules.getCategory("object");

    catConstructor = rules.getCategory("constructor");
    catParameter = rules.getCategory("parameter");
    catParameters = rules.getCategory("parameters");
    catName = rules.getCategory("name");
    catDefault = rules.getCategory("default");
    catCode = rules.getCategory("code");
    catMethod = rules.getCategory("method");
    catFunction = rules.getCategory("function");
    catAtIndex = rules.getCategory("atIndex");
    catValue = rules.getCategory("value");
    catIfOp = rules.getCategory("ifOp");
    
    catIf = rules.getCategory("if");
    catCondition = rules.getCategory("condition");
    catElse = rules.getCategory("else");
    catDo = rules.getCategory("do");
    catFor = rules.getCategory("for");
    catInit = rules.getCategory("init");
    catIteration = rules.getCategory("iteration");
    catForEach = rules.getCategory("for_each");
    catFunctionCall = rules.getCategory("functionCall");
    catCondition = rules.getCategory("condition");
    catIncrement = rules.getCategory("increment");
    catReturn = rules.getCategory("return");
    
    catThis = rules.getCategory("this");
    catGlobal = rules.getCategory("global");
    catEquate = rules.getCategory("equate");
    catDot = rules.getCategory("dot");
    catVariable = rules.getCategory("variable");
    catField = rules.getCategory("field");
    catType = rules.getCategory("type");
    catStatic = rules.getCategory("static");
    catNew = rules.getCategory("new");
    
    setFunction(rules, "integer", new Function(cInteger));
    setFunction(rules, "string stringSequence", new Function(cString));
    //setFunction(rules, "array", cArray);
    setFunction(rules, "addition", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        Scope type0 = scope.getType(node.first());
        Scope type1 = scope.getType(node.last());
        if(type0 == cString || type1 == cString) return cString;
        if(type0 == cFloat || type1 == cFloat) return cFloat;
        return cInteger;
      }
    });
    setFunction(rules, "subtraction multiplication division", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        Scope type0 = scope.getType(node.first());
        Scope type1 = scope.getType(node.last());
        if(type0 == cFloat || type1 == cFloat) return cFloat;
        return cInteger;
      }
    });
    setFunction(rules
        , "more less moreOrEqual lessOrEqual equal notEqual not and or"
        , new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        return cBoolean;
      }
    });
    setFunction(rules, "brackets ifOp", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        return scope.getType(node.last());
      }
    });
    setFunction(rules, "variable", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        Scope varScope;
        if(node.hasChild(catThis)) {
          varScope = scope.findField(node.caption);
        } else {
          varScope = scope.findVariable(node.caption);
        }
        if(varScope == null) error("Variable" + node.caption + " is not found.");
        if(varScope.parentClass != null) return varScope;
        if(varScope.type == null) varScope.setTypes();
        return varScope.type;
      }
    });
    setFunction(rules, "dot", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        return scope.getType(node.first()).findField(node.last().caption);
      }
    });
    setFunction(rules, "functionCall", new Function() {
      @Override
      Scope getType(Node node, Scope scope) {
        Scope callType = scope.getType(node.first());
        if(callType.parentClass != null) node.type = catNew;
        return callType;
      }
    });
    
    typeSwitch.put("Integer", "long");
    typeSwitch.put("Float", "double");
  }
  
  public Scope processModule(Module module) {
    initCodeScopes(module.rootNode, rootScope, true);
    setCodeTypes(module.rootNode, rootScope);
    rootScope.setTypes();
    process(module.rootNode);
    return rootScope;
  }
  
  void initCodeScopes(Node codeNode, Scope codeScope) {
    initCodeScopes(codeNode, codeScope, false);
  }
  
  void initCodeScopes(Node codeNode, Scope codeScope, boolean checkVariables) {
   if(codeNode == null) return;
    for(Node childNode : codeNode.children) {
      if(childNode.type == catClass) {
        initClassScopes(childNode, codeScope.get(childNode.caption, cClass));
      } else if(childNode.type == catEquate) {
        Node varNode = childNode.first();
        if(checkVariables || varNode.findChild(catGlobal) != null) {
          if(varNode.type == catVariable) rootScope.get(varNode.caption);
        }
      } else if(childNode.type == catFunction) {
        initFunctionScopes(childNode, codeScope.get(childNode.caption), false);
      } else if(childNode.type == catIf) {
        initCodeScopes(childNode.findChild(catCode), codeScope);
        initCodeScopes(childNode.findChild(catElse), codeScope);
      } else if(childNode.type == catFor || childNode.type == catForEach) {
        initCodeScopes(childNode.findChild(catCode), codeScope);
      }
    }
  }
  
  void initClassScopes(Node classNode, Scope classScope){
    for(Node child : classNode.children) {
      if(child.type == catConstructor) {
        initFunctionScopes(child, classScope.get(child.caption), true);
      } else if(child.type == catMethod) {
        initFunctionScopes(child, classScope.get(child.caption), false);
      } else if(child.type == catField) {
        Scope fieldScope = classScope.get(child.caption);
        Node typeNode = child.findChild(catType);
        if(typeNode != null) {
          Scope typeScope = classScope.findVariable(typeNode.caption);
          if(typeScope == null) error("Class " + typeNode.caption
              + " is not found.");
          if(typeScope.parentClass == null) error(typeNode.caption
              + " is not a class.");
          fieldScope.type = typeScope;
        }
      }
    }
  }
  
  void initFunctionScopes(Node funcNode, Scope funcScope, boolean isConstructor) {
    Node codeNode = funcNode.findChild(catCode);
    for(Node param : funcNode.children) {
      if(param.type == catParameter && param.findChild(catThis) == null)
        funcScope.get(param.caption);
    }
    initCodeScopes(codeNode, funcScope, false);
  }
  
  

  void setCodeTypes(Node node, Scope scope) {
    setCodeTypes(node, scope, true);
  }
  
  void setCodeTypes(Node codeNode, Scope codeScope, boolean newScopeForVar) {
    if(codeNode == null) return;
    for(Node childNode : codeNode.children) {
      if(childNode.type == catClass) {
        setClassTypes(childNode, codeScope.get(childNode.caption, cClass));
      } else if(childNode.type == catFunction) {
        setFunctionTypes(childNode, codeScope.get(childNode.caption), false);
      } else if(childNode.type == catEquate) {
        Node varNode = childNode.first();
        if(varNode.type == catVariable) {
          if(!varNode.hasChild(catThis)) {
            Scope varScope = codeScope.findVariable(varNode.caption);
            if(varScope == null) {
              codeScope = new Scope("", codeScope, null);
              varScope = codeScope.get(varNode.caption);
            }
            varScope.setInit(varNode, childNode.last(), codeScope);
          }
        }
      } else if(childNode.type == catReturn) {
        codeScope.setInit(codeNode.parent, childNode.first(), codeScope);
      } else if(childNode.type == catIf) {
        Scope ifScope = new Scope(codeScope);
        setCodeTypes(childNode.findChild(catCode), ifScope);
        setCodeTypes(childNode.findChild(catElse), ifScope);
      } else if(childNode.type == catFor) {
        Scope forScope = new Scope(codeScope);
        setCodeTypes(childNode.findChild(catInit), forScope, false);
        setCodeTypes(childNode.findChild(catIteration), forScope);
        setCodeTypes(childNode.findChild(catCode), forScope);
      }
    }
  }

  void setClassTypes(Node classNode, Scope classScope) {
    for(Node child : classNode.children) {
      if(child.type == catConstructor) {
        setFunctionTypes(child, classScope.get(child.caption), true);
      } else if(child.type == catMethod) {
        setFunctionTypes(child, classScope.get(child.caption), false);
      } else if(child.type == catField) {
        Node valueNode = child.findChild(catDefault);
        if(valueNode != null) classScope.get(child.caption).setInit(child
            , valueNode.first(), classScope);
      }
    }
  }
  
  void setFunctionTypes(Node funcNode, Scope funcScope, boolean isConstructor) {
    Node codeNode = funcNode.findChild(catCode);
    for(Node param : funcNode.children) {
      if(param.type == catParameter) {
        if(!param.hasChild(catThis)) {
          Node defaultNode = param.findChild(catDefault);
          if(defaultNode != null) funcScope.get(param.caption).setInit(param
              , defaultNode, funcScope.parent);
        }
      }
    }
    setCodeTypes(codeNode, funcScope);
  }
  
  
  HashMap<String, String> typeSwitch = new HashMap<>();
  Node mainCodeNode, mainClassNode;
  
  void process(Node node) {
    mainCodeNode = new Node(catCode);
    mainClassNode = new Node(catClass, "Main", new Node(catMethod, "main"
        , new Node(catParameter, "args", new Node(catType, "String[]"))
        , new Node(catType, "void"), new Node(catStatic), mainCodeNode));
    processNode(node);
    
    LinkedList<Node> toClass = new LinkedList<>();
    LinkedList<Node> toMethod = new LinkedList<>();
    for(Node child : node.children) {
      if(child.type == catClass) {
        processClass(child);
        toClass.add(child);
      } else {
        toMethod.add(child);
      }
    }
    for(Node child : toMethod) child.moveTo(mainCodeNode);
    for(Node child : toClass) child.moveTo(mainClassNode);
    
    node.add(mainClassNode);
  }
  
  void processCode(Node codeNode) {
    for(Node child : codeNode.children) {
      if(child.type == catClass) processClass(child);
    }
  }
  
  void processClass(Node classNode) {
    for(Node child : classNode.children) {
      if(child.type == catConstructor) {
        child.caption = classNode.caption;
        LinkedList<Node> codeLines = child.findChild(catCode).children;
        for(Node param : child.children) {
          if(param.type == catParameter && param.hasChild(catThis)) {
            String caption = param.caption;
            int index = 0;
            for(Node field : classNode.children) {
              if(field.caption.equals(caption)) {
                param.getChild(catType).caption = field.getChild(catType).caption;
                param.removeChild(catThis);
                codeLines.add(index, new Node(catEquate, "", new Node(
                    catVariable, caption, new Node(catThis)), new Node(
                    catVariable, caption)));
                index++;
              }
            }
          }
        }
      } else if(child.type == catMethod) {
        if(!child.hasChild(catType)) child.getChild(catType).caption = "void";
      }
    }
  }
    
  void processNode(Node node) {
    Node typeNode = node.findChild(catType);
    if(typeNode != null) {
      String newType = typeSwitch.get(typeNode.caption);
      if(newType != null) typeNode.caption = newType;
    }
    if(node.hasChild(catGlobal)) {
      mainClassNode.add(new Node(catField, node.caption, new Node(catType
          , node.findChild(catType).caption), new Node(catStatic)));
      node.removeChild(catType);
    }
    if(node.type == catFunctionCall) {
      Node callNode = node.first();
      if(callNode.caption.equals("print")) callNode.caption = "System.out.println";
    }
    for(Node child : node.children) processNode(child);
  }
  
  public static void error(String message) {
    Base.error("Processing error", message);
  }
}