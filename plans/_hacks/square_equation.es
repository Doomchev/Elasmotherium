Array<Float> solve(Float a, Float b, Float c) {
	if(a == 0 && b == 0) return [];
	if(a == 0) return [-c / b];
	Float d = b * b - 4 * a * c;
	if(d < 0) return [];
	if(d == 0) return [-b / (2 * a)];
	return [(-b - sqrt(d)) / (2 * a), (-b + sqrt(d)) / (2 * a)];
}

manualTest() {
	Float a = askFloat("Enter a in ax2 + bx + c = 0 formula");
	Float b = askFloat("Enter b in ax2 + bx + c = 0 formula");
	Float c = askFloat("Enter c in ax2 + bx + c = 0 formula");
	Array[Float] solutions = solve(a, b, c);
	switch(solutions.size) {
		case 0:
			tell("There are no solutions");
		case 1:
			tell("There is one solution: \(solutions[0])");
		case 2:
			tell("There are two solutions: \(solutions[0]) and \(solutions[1])");
	}
}

tests() {
	assert(solve(5, 6, 1) == [-0.2, -1]);
}