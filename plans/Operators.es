classlist Number {I64, F64}
classlist Concatenable {I64, F64, String}
classlist Any {I64, F64, String, Question, Class, Object}

Type variable = v1;

variable = v1;
leftSide.field = v1;
leftSide[indexes] = v1;

leftSide = leftSide.field
leftSide = leftSide.function(params)
leftSide = leftSide[indexes]



variable() {
	addToScope(name);
	value.resolve(type);
	command("variable", type, "equate");
}



equate() {
	v0.resolveVar("equate", v1);
}

add() {
	v0.resolveVar("add", v1);
}

subtract() {
	v0.resolveVar("subtract", v1);
}

multiply() {
	v0.resolveVar("multiply", v1);
}

divide() {
	v0.resolveVar("divide", v1);
}

increment() {
	v0.resolveVar("increment");
}

decrement() {
	v0.resolveVar("decrement");
}



id.resolveVar(function, value) {
	object = getFromScope(name);
	object.resolveVar(function, value);
}

variable.resolveVar(function, value) {
	value.resolve(type);
	command("variable", type, function);
}

dot.resolveVar(function, value) {
	objectType = v0.resolveObject();
	fieldType = objectType.getField(field);
	command("field", fieldType, function);
}

object.resolveVar(function, value) {
	
	command("field", fieldType, function);
}



id.resolveObject() {
	command("object", fieldType, function);
}


id.resolveObject() {
	object = getFromScope(name);
	return object.resolveObject();
}

variable.resolveObject() {
	command("ObjectStackPush");
	return type;
}

object.resolveObject() {
	
}

dot.resolveObject() {
	
}


[increment, decrement]:3() {
	v0Resolve v0, v0Type
	func v0
}

[iDivide, mode]:3(v0, x) {
	resolve x
	func v0
}

[subtract, multiply, divide]:3(v0, x) {
	resolve x
	convert x, v0
	func v0
}

brackets:18(x) {
	resolve x
	return x
}

negative:16(x) {
	resolve x, Number
	negative x
	return x
}

not:16(x) {
	resolve x, Question
	not x
	return Question
}

[iDivision, mod]:14(x, y) {
	resolve x, I64
	resolve y, I64
	func I64
	return I64
}

division:14(x, y) {
	resolve x, F64
	resolve y, F64
	division F64
	return F64
}

[subtraction, multiplication]:[13, 14](x, y) {
	resolve x, Concatenable(x, y)
	resolve y, Concatenable(x, y)
	func Concatenable(x, y)
	return Concatenable(x, y)
}

addition:13(x, y) {
	resolve x
	convert x, max(x, y)
	resolve y
	convert y, max(x, y)
	addition max(x, y)
	return max(x, y)
}

[less, more, lessOrEqual, moreOrEqual](Number x, Number y) {
	priority 8
	resolve x
	convert x, max(x, y)
	resolve y
	convert y, max(x, y)
	func max(x, y)
	return Question
}

[equal, notEqual](Any x, Any y) {
	priority 7
	resolve x
	convert x, max(x, y)
	resolve y
	convert y, max(x, y)
	func max(x, y)
	return Question
}

[or, and](Question x, Question y) {
	priority [5, 6]
	resolve x
	resolve y
	func
	return Question
}

ifOp(Question condition, Any ifTrue, Any ifFalse) {
	priority 4
	resolve condition
	ifFalseGoto false
	resolve ifTrue
	convert ifTrue, max(ifTrue, ifFalse)
	goto end
	false:
	resolve ifFalse
	convert ifFalse, max(ifTrue, ifFalse)
	end:
	return max(ifTrue, ifFalse)
}

add(variable.Concatenable v0, Concatenable x) {
	priority 3
	resolve x
	convert x, v0
	add v0
}

#blocks

for {
	resolve variable
	resolve init
	equate variable
	start:
	resolve condition
	ifFalseGoto break
	resolve code
	continue: store
	resolve iterator
	goto start
	break: store
}

break {
	goto break
}

continue {
	goto continue
}

do {
	start:
	resolve code
	goto start
}

if {
	resolve condition
	ifFalseGoto else
	resolve code
	goto end
	else:
	resolve else
	end:
}

#functions

println(Concatenable text) {
	resolve text
	convert text, String
	println
}

input(Concatenable text) {
	resolve text
	convert text, String
	input
	return String
}

random(I64 x) {
	resolve x
	random
	return I64
}

showMessage(Concatenable text) {
	resolve text
	convert text, String
	println
}