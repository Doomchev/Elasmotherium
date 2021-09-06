List<Int> values = [2];
for(Int i from 3 to 20) {
	Int limit = floor(sqrt(i));
	for(each Int j in values) {
		if(j > limit) {
			values.add(i);
			break;
		}
		if(i % j == 0) break;
	}
}
String text = "";
for(each Int i in values) text = text + ", \(i)";
println(text);