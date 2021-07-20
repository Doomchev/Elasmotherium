Int max = askInteger("Enter max value:");
List<Int> values = [];
for(Int i = 2 ..< max) {
	Int limit = floor(sqrt(i));
	for(Int j: values) {
		if(j > limit) {
			values.add(i);
			break;
		}
		if(i % j == 0) break;
	}
}
for(Int i: values) print((first ? "" : ", ") + i);