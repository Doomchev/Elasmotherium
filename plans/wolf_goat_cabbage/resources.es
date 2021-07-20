Position extends Enum {
	left
	inBoat
	right
}

boat = Sprite() {
	position: left
}
wolf = Sprite() {
	position: left
}
goat = Sprite() {
	position: left
}
cabbage = Sprite() {
	position: left
}

Scene = Layer() {
	background
	wolf
	goat
	cabbage
	boat
}

Sprite spriteInBoat