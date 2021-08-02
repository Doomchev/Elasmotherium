class CoordSpeed<AnyFloat SystemFloat> extends Point {
	SystemFloat xSpeed, ySpeed;
	
	moveForward() {
		x += xSpeed * frameTime;
		y += ySpeed * frameTime;
	}
}