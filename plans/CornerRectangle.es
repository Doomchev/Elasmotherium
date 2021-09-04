class CornerRectangle<AnyFloat SystemFloat> extends Point<SystemFloat> {
	SystemFloat width, height;
	
	SystemFloat halfWidth -> 0.5 * width;
	halfWidth(SystemFloat value) width = 2 * value;
	
	SystemFloat halfHeight -> 0.5 * height;
	halfHeight(SystemFloat value) height = 2 * value;
	
	SystemFloat left <-> x;
	
	SystemFloat centerX -> x + halfWidth;
	centerX(SystemFloat value) x = value - halfWidth;
	
	SystemFloat right -> x + width;
	rightX(SystemFloat value) x = value - width;
	
	SystemFloat top <-> y;
	
	SystemFloat centerY -> y + halfHeight;
	centerY(SystemFloat value) y = value - halfHeight
	
	SystemFloat bottom -> y + height;
	bottom(SystemFloat value) y = value - height;
}