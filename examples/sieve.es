Int max = 100;
List<Int> values = [2];
for(Int i = 3 ..= max) {
	Int limit = floor(sqrt(i));
	for(Int j: values) {
		if(j > limit) {
			values.add(i);
			break;
		}
		if(i % j == 0) break;
	}
}
String text = "";
for(Int i: values) text = text + ", \(i)";
println(text);