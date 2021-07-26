classlist Number {I64, F64}
classlist Concatenable {I64, F64, String}
classlist Any {I64, F64, String, Bool, Class, Object}

var.dot(object, id) {
	class = resolveValue(value);
	fieldClass = field(class, id);
	varType = field;
}

var.id(id) {
	object = 
}

value.id(id) {
	if(id = "this") 
}

equate(var, x) {
	varClass = resolveVar(var);
	valueClass = resolveValue(x, varClass);
	switch(varClass) {
		case I64: 
			
	}
}

equate(

var.id() {
	
}

var.dot(object, field) {
	objectClass = var.resolve(object)
	fieldClass = getField(objectClass, field)
	return(fieldClass)
}


[increment, decrement]:3(var) {
	varResolve var, varType
	func var
}

[iDivide, mode]:3(var, x) {
	resolve x
	func var
}

[subtract, multiply, divide]:3(var, x) {
	resolve x
	convert x, var
	func var
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
	resolve x, Bool
	not x
	return Bool
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
	return Bool
}

[equal, notEqual](Any x, Any y) {
	priority 7
	resolve x
	convert x, max(x, y)
	resolve y
	convert y, max(x, y)
	func max(x, y)
	return Bool
}

[or, and](Bool x, Bool y) {
	priority [5, 6]
	resolve x
	resolve y
	func
	return Bool
}

ifOp(Bool condition, Any ifTrue, Any ifFalse) {
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

add(Variable.Concatenable var, Concatenable x) {
	priority 3
	resolve x
	convert x, var
	add var
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