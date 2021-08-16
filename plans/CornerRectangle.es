class CornerRectangle<AnyFloat SystemFloat> extends Point<SystemFloat> {
	SystemFloat width, height;
	
	SystemFloat halfWidth -> 0.5 * width;
	halfWidth(SystemFloat value) width = 2 * value;
	
	SystemFloat halfHeight -> 0.5 * height;
	halfHeight(SystemFloat value) height = 2 * value;
	
	SystemFloat left <-> _x;
	
	SystemFloat centerX -> _x + halfWidth;
	centerX(SystemFloat value) _x = value - halfWidth;
	
	SystemFloat right -> _x + width;
	rightX(SystemFloat value) _x = value - width;
	
	SystemFloat top <-> _y;
	
	SystemFloat centerY -> _y + halfHeight;
	centerY(SystemFloat value) _y = value - halfHeight
	
	SystemFloat bottom -> _y + height;
	bottom(SystemFloat value) _y = value - height;
}