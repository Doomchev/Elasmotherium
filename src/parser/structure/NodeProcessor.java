package parser.structure;

import base.Base;
import base.Module;
import java.util.HashMap;
import java.util.LinkedList;
import parser.Category;
import parser.Rules;

public final class NodeProcessor {
  ClassScope newClass(String name, ClassScope parentClass) {
    Node classNode = new Node(catClass, name);
    ClassScope scope = new ClassScope(classNode, rootScope, parentClass);
    classNode.structure = scope;
    rootScope.children.put(name, scope);
    return scope;
  }

  Structure commonClass(Structure structure1, Structure structure2) {
    LinkedList<Scope> scopes = new LinkedList<>();
    ClassScope classScope = structure1.resolve();
    while(classScope != rootScope) {
      scopes.add(classScope);
      classScope = classScope.parentClass;
    }
    classScope = structure2.resolve();
    while(classScope != rootScope) {
      if(scopes.contains(classScope)) {
        if(classScope.typeParam == null) return classScope;
        Structure type = new Structure(null, classScope);
        int length = classScope.typeParam.length;
        type.typeParam = new Structure[length];
        for(int n = 0; n < length; n++) {
          type.typeParam[n] = commonClass(structure1.typeParam[n]
              , structure2.typeParam[n]);
        }
        return type;
      }
      classScope = classScope.parentClass;
    }
    return null;
  }
  
  ClassScope rootScope = new ClassScope();
  ClassScope cObject = newClass("Object", rootScope);
  ClassScope cBoolean = newClass("Boolean", rootScope);
  ClassScope cConcatenable = newClass("Concatenable", rootScope);
  ClassScope cNumber = newClass("Number", cConcatenable);
  ClassScope cInteger = newClass("Integer", cNumber);
  ClassScope cFloat = newClass("Float", cNumber);
  ClassScope cString = newClass("String", cConcatenable);
  HashMap<Category, Function> functions = new HashMap<>();
  Category catClass, catConstructor, catMethod, catThis, catParameter, catReturn
      , catDefault, catName, catCode, catFunction, catDo, catFor, catForEach
      , catEquate, catDot, catVariable, catField, catFunctionCall, catAtIndex
      , catParameters, catInteger, catString, catIncrement, catValue, catType
      , catIf, catCondition, catElse, catInit, catIteration, catStatic, catNew
      , catIfOp, catObject, catGlobal, catGet, catSubType, catNative;

  void setFunction(Rules rules, String categories, Function func) {
    for(String categoryName : categories.split(" "))
      functions.put(rules.getCategory(categoryName), func);
  }
  
  boolean isConcatenable(Scope scope) {
    return scope == cString || scope == cFloat || scope == cInteger;
  }
  
  public NodeProcessor(Rules rules) {
    catClass = rules.getCategory("class");
    catInteger = rules.getCategory("integer");
    catString = rules.getCategory("string");
    catObject = rules.getCategory("object");
    catNative = rules.getCategory("native");

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
    catGet = rules.getCategory("get");
    
    catThis = rules.getCategory("this");
    catGlobal = rules.getCategory("global");
    catEquate = rules.getCategory("equate");
    catDot = rules.getCategory("dot");
    catVariable = rules.getCategory("variable");
    catField = rules.getCategory("field");
    catType = rules.getCategory("type");
    catStatic = rules.getCategory("static");
    catNew = rules.getCategory("new");
    catSubType = rules.getCategory("subtype");
    
    setFunction(rules, "integer", new Function(cInteger));
    setFunction(rules, "string", new Function(cString));
    setFunction(rules, "stringSequence", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        for(Node child : node.children) {
          Structure childType = getType(child, scope);
          if(!childType.isChildOf(cConcatenable)) error("Cannot add "
              + child.caption + "to string.");
        }
        return cString;
      }
    });
    setFunction(rules, "addition", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        Structure type0 = getType(node.first(), scope);
        Structure type1 = getType(node.last(), scope);
        if(!type0.isChildOf(cConcatenable) || !type1.isChildOf(cConcatenable))
          error("Value for " + node.caption + " must be number or string.");
        if(type0 == cString || type1 == cString) return cString;
        if(type0 == cFloat || type1 == cFloat) return cFloat;
        return cInteger;
      }
    });
    setFunction(rules, "subtraction multiplication division", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        Structure type0 = getType(node.first(), scope);
        Structure type1 = getType(node.last(), scope);
        if(!type0.isChildOf(cNumber) || !type1.isChildOf(cNumber))
          error("Value for " + node.caption + " must be number.");
        if(type0 == cFloat || type1 == cFloat) return cFloat;
        return cInteger;
      }
    });
    setFunction(rules, "more less moreOrEqual lessOrEqual", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        if(!getType(node.first(), scope).isChildOf(cNumber) || !getType(
            node.last(), scope).isChildOf(cNumber))
            error("Values of comparsion must be numbers.");
        return cBoolean;
      }
    });
    setFunction(rules, "equal notEqual", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        return cBoolean;
      }
    });
    setFunction(rules, "not", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        if(getType(node.first(), scope) != cBoolean)
          error("! value must be boolean.");
        return cBoolean;
      }
    });
    setFunction(rules, "and or", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        if(getType(node.first(), scope) != cBoolean || getType(node.last(), scope)
            != cBoolean) error("Values of " + node.caption + " must be boolean.");
        return cBoolean;
      }
    });
    setFunction(rules, "brackets", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        return getType(node.last(), scope);
      }
    });
    setFunction(rules, "ifOp", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        if(getType(node.first(), scope) != cBoolean)
          error("Condition of ternary operator must be boolean.");
        Structure classScope = commonClass(getType(node.last(), scope)
            , getType(node.children.get(1), scope));
        if(classScope == null) error("Values of ternary operator has no common class");
        return classScope;
      }
    });
    setFunction(rules, "variable", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        return variableType(node, scope);
      }
    });
    setFunction(rules, "dot", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        Node lastNode = node.last();
        Structure nodeType = getType(node.first(), scope);
        Structure typeClass = nodeType.toClass();
        if(typeClass == null) typeClass = nodeType.type;
        Structure field = typeClass.findField(lastNode.caption);
        lastNode.structure = field;
        if(field.node.hasChild(catGet)) lastNode.getChild(catGet);
        return field.type.resolveType(nodeType);
      }
    });
    setFunction(rules, "functionCall", new Function() {
      @Override
      Structure calcType(Node node, Scope scope) {
        Node callNode = node.first();
        if(scope.findVariable(callNode.caption).getClass() != null)
          node.category = catNew;
        return getType(callNode, scope);
      }
    });
  }
  
  Node rootNode;
  LinkedList<Node> globalVariables = new LinkedList<>();
  
  public Scope processModule(Module module) {
    rootNode = module.rootNode;
    rootNode.structure = rootScope;
    rootScope.node = rootNode;
    initCodeScope(rootNode, rootScope, true);
    for(Node node : globalVariables) rootNode.children.addFirst(node);
    setTypes(rootNode, rootScope);
    
    Structure listStructure = rootScope.findField("List");
    listStructure.nativeName = "LinkedList";
    listStructure.findField("first").nativeName = "getFirst";
    rootScope.findField("Integer").nativeName = "long";
    
    process(rootNode);
    return rootScope;
  }

  void setTypes(Node node, Scope scope) {
    Scope nodeScope = node.getScope();
    if(nodeScope != null) scope = nodeScope;
    if(node.category == catReturn) {
      setType(scope.node, getType(node.first(), scope));
    } else if(node.category == catFunction || node.category == catMethod
         || node.category == catGet) {
      for(Structure structure : nodeScope.children.values()) {
        variableType(structure.node, nodeScope);
      }
      setTypes(node.getChild(catCode), scope);
    } else if(node.category == catEquate) {
      setTypes(node.first(), scope);
    } else if(node.category == catVariable || node.category == catField
         || node.category == catParameter) {
      variableType(node, scope);
    } else if(node.category == catFunctionCall) {
      for(Node child : node.last().children) getType(child, scope);
    } else if(node.category == catDot) {
    } else {
      for(Node child : node.children) setTypes(child, scope);
    }
  }
  
  Structure variableType(Node node, Scope scope) {
    Structure structure = node.structure;
    if(structure == null) {
      if(node.hasChild(catThis)) {
        structure = scope.findField(node.caption);
      } else {
        structure = scope.findVariable(node.caption);
      }
      if(structure == null) error("Variable " + node.caption + " is not found.");
      node.structure = structure;
      if(node.hasChild(catSubType)) {
        Structure subType = new Structure(node, null);
        subType.type = structure;
        int index = 0;
        subType.typeParam = new Structure[node.children.size()];
        for(Node child : node.children) {
          subType.typeParam[index] = variableType(child, scope);
          index++;
        }
        return subType;
      }
    }
    ClassScope classScope = structure.toClass();
    if(classScope != null) return classScope;
    if(structure.type == null) {
      Node definitionNode = structure.node;
      Node typeNode = definitionNode.findChild(catType);
      if(typeNode == null) {
        if(definitionNode.category == catVariable) {
          setType(definitionNode, getType(definitionNode.parent.last(), scope));
        } else if(definitionNode.category == catField || definitionNode.category
            == catParameter) {
          Node getNode = definitionNode.findChild(catGet);
          if(getNode != null) {
            setTypes(getNode, scope);
            setType(definitionNode, getNode.structure);
          } else {
            Node defaultNode = definitionNode.findChild(catDefault);
            if(defaultNode == null) error("Cannot figure out the type.");
            setType(definitionNode, getType(defaultNode.first(), scope));
          }
        } else {
          error(definitionNode.category.name + " is not supported");
        }
      } else {
        Structure classStructure = scope.findType(typeNode.caption);
        if(classStructure == null) error("Type " + typeNode.caption
            + " is not found.");
        setType(definitionNode, classStructure);
        typeNode.structure = classStructure;
        return structure.type;
      }
    }
    return structure;
  }
  
  void setType(Node node, Structure structure) {
    Node typeNode = node.getChild(catType);
    if(structure.toClass() == null && structure.toSubType() == null) {
      node.structure.type = structure.type;
      node.structure.typeParam = structure.typeParam;
      typeNode.caption = structure.type.node.caption;
      typeNode.structure = structure.type;
      if(structure.node.hasChild(catGet)) {
        node.getChild(catGet);
      }
      addStructure(typeNode, structure);
    } else {
      node.structure.type = structure;
      typeNode.caption = structure.node.caption;
      typeNode.structure = structure;
    }
  }
  
  void addStructure(Node node, Structure structure) {
    if(structure.typeParam == null) return;
    for(Structure subTypeStructure : structure.typeParam) {
      Node subTypeNode = new Node(catSubType, subTypeStructure.node.caption);
      addStructure(subTypeNode, subTypeStructure);
      subTypeNode.structure = subTypeStructure;
      node.add(subTypeNode);
    }
  }
  
  Structure getType(Node node, Scope scope) {
    Function func = functions.get(node.category);
    if(func == null) error(node.category.name + " is not supported");
    return func.calcType(node, scope);
  }
  
  void initCodeScope(Node codeNode, Scope scope) {
    initCodeScope(codeNode, scope, false);
  }
  
  void initCodeScope(Node codeNode, Scope scope, boolean checkVariables) {
    if(codeNode == null) return;
    for(Node childNode : codeNode.children) {
      if(childNode.category == catClass) {
        initClassScope(childNode);
      } else if(childNode.category == catEquate) {
        Node varNode = childNode.first();
        if(varNode.category == catVariable) initType(varNode, scope);
      } else if(childNode.category == catFunction) {
        initFunctionScope(childNode, false);
      } else if(childNode.category == catIf) {
        initCodeScope(childNode.findChild(catCode), scope);
        initCodeScope(childNode.findChild(catElse), scope);
      } else if(childNode.category == catFor || childNode.category == catForEach) {
        initCodeScope(childNode.findChild(catCode), scope);
      }
    }
  }
  
  void initClassScope(Node classNode){
    ClassScope scope = new ClassScope(classNode, rootScope, cObject);
    classNode.structure = scope;
    int index = 0;
    for(Node child : classNode.children) {
      if(child.category == catConstructor) {
        initFunctionScope(child, true);
      } else if(child.category == catMethod) {
        initFunctionScope(child, false);
      } else if(child.category == catField) {
        initType(child, scope);
        Node getNode = child.findChild(catGet);
        if(getNode != null) initFunctionScope(getNode, scope, false);
      } else if(child.category == catSubType) {
        SubType subType = new SubType(child, scope, index);
        child.structure = subType;
        scope.children.put(child.caption, subType);      }
    }
  }
  void initFunctionScope(Node funcNode, boolean isConstructor) {
    initFunctionScope(funcNode, funcNode.parent.getScope(), isConstructor);
  }
  
  void initFunctionScope(Node funcNode, Scope parentScope, boolean isConstructor) {
    Scope scope = new Scope(funcNode, parentScope);
    funcNode.structure = scope;
    for(Node param : funcNode.children) {
      if(param.category == catParameter && !param.hasChild(catThis))
        initType(param, scope);
    }
    initCodeScope(funcNode.findChild(catCode), scope, false);
  }
  
  void initType(Node node, Scope scope) {
    Structure structure = new Structure(node, scope);
    node.structure = structure;
    scope.children.put(node.caption, structure);
  }
  
  
  HashMap<String, String> typeSwitch = new HashMap<>();
  Node mainCodeNode, mainClassNode;
  
  void process(Node node) {
    mainCodeNode = new Node(catCode);
    mainClassNode = new Node(catClass, "Main", new Node(catMethod, "main"
        , new Node(catParameter, "args", new Node(catType, "String[]"))
        , new Node(catType, "public void"), new Node(catStatic), mainCodeNode));
    processNode(node);
    
    //LinkedList<Node> toClass = new LinkedList<>();
    LinkedList<Node> toMethod = new LinkedList<>();
    LinkedList<Node> remove = new LinkedList<>();
    for(Node child : node.children) {
      if(child.hasChild(catNative)) {
        remove.add(child);
      } else if(child.category == catClass) {
        processClass(child);
        //toClass.add(child);
      } else {
        toMethod.add(child);
      }
    }
    for(Node child : toMethod) child.moveTo(mainCodeNode);
    for(Node child : remove) node.children.remove(child);
    //for(Node child : toClass) child.moveTo(mainClassNode);
    
    node.add(mainClassNode);
  }
  
  void processCode(Node codeNode) {
    for(Node child : codeNode.children) {
      if(child.category == catClass) processClass(child);
    }
  }
  
  void processClass(Node classNode) {
    for(Node child : classNode.children) {
      if(child.category == catConstructor) {
        child.caption = classNode.caption;
        LinkedList<Node> codeLines = child.findChild(catCode).children;
        for(Node param : child.children) {
          if(param.category == catParameter && param.hasChild(catThis)) {
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
      } else if(child.category == catMethod) {
        if(!child.hasChild(catType)) child.getChild(catType).caption = "void";
      }
    }
  }
    
  void processNode(Node node) {
    if(node.structure != null && node.structure.nativeName != null)
      node.caption = node.structure.nativeName;
    
    if(node.hasChild(catGlobal)) {
      mainClassNode.add(new Node(catField, node.caption, new Node(catType
          , node.findChild(catType).caption), new Node(catStatic)));
      node.removeChild(catType);
    }
    //if(node.category == catVariable && node.base != null) {
    //  if(node.base.hasChild(catGlobal)) node.caption = "Main." + node.caption;
    //} else 
    if(node.category == catFunctionCall) {
      Node callNode = node.first();
      if(callNode.caption.equals("print")) callNode.caption = "System.out.println";
    }
    for(Node child : node.children) processNode(child);
  }
  
  public static void error(String message) {
    Base.error("Processing error", message);
  }
}