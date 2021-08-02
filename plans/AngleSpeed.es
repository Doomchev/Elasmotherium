class AngleSpeed<AnyFloat SystemFloat> extends Point<AnyFloat> {
	SystemFloat angle, speed;
	
	moveForward() {
		SystemFloat delta = speed * frameTime;
		centerX += cos(angle) * delta;
		centerY += sin(angle) * delta;
	}
	
	directTo(Point point) angle = atn2(centerY - point.centerY, x - point.centerX);
}