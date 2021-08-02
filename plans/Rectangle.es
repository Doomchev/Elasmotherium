class Rectangle<AnyFloat SystemFloat> extends Point {
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
	
	SystemFloat left -> _x - halfWidth;
	left(SystemFloat value) _x = value + halfWidth;
	
	SystemFloat right -> _x + halfWidth;
	right(SystemFloat value) _x = value - halfWidth;
	
	SystemFloat top -> _y - halfHeight;
	top(SystemFloat value) _y = value + halfHeight;
	
	SystemFloat bottom -> _y + halfHeight;
	bottom(SystemFloat value) _y = value - halfHeight;
}