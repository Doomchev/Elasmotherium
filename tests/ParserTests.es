call();
call(var + 1, k[10]().field);
Int square(Int x) -> x * x;
Int function() {
	Int y = 0;
	for(x = 0; x < 9; x++) y += x;
	return y;
}
beep() PlaySound("beep.wav");
Int x = 1;
x = 2;
x[1] = 5;
x.field[2].vie(1).a /= 3;


// if clause
if(0 == 0) print("0 == 0");
if(0 == 1) print("0 == 1"); else print("0 != 1");
if(0 == 1) {
  print("0 == 1");
} else if(0 == 2) {
  print("0 == 2");
} else print("0 != 2");

// for loop
List<String> list = ["zero", "one", "two"];
for(Int index = 0 ..< list.size) print("\(list[index]) = \(index)");
for(String value: list) print(value);
for(String value[Int index]: list) print("\(value) = \(index)");
for([Int index]: list) print(index);
for(Int index = 0; index < list.size; index++) print("\(list[index]) = \(index)");
for(Int index = 0; index < list.size) print("\(list[index]) = \(index)");