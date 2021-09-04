class Rectangle<AnyFloat SystemFloat> extends Point<SystemFloat> {
	SystemFloat halfWidth, halfHeight;
	
	SystemFloat width -> 2 * halfWidth;
	width(SystemFloat value) halfWidth = 0.5 * value;
	
	SystemFloat height -> 2 * halfWidth;
	height(SystemFloat value) halfHeight = 0.5 * value;
	
	SystemFloat radius -> halfWidth;
	radius(SystemFloat value) {
		halfWidth = value;
		halfHeight = value;
	}
	
	SystemFloat size -> width;
	size(SystemFloat value) {
		width = value;
		height = value;
	}
	
	SystemFloat left -> x - halfWidth;
	left(SystemFloat value) x = value + halfWidth;
	
	SystemFloat right -> x + halfWidth;
	right(SystemFloat value) x = value - halfWidth;
	
	SystemFloat top -> y - halfHeight;
	top(SystemFloat value) y = value + halfHeight;
	
	SystemFloat bottom -> y + halfHeight;
	bottom(SystemFloat value) y = value - halfHeight;
}