Tiles extends Enum<Color> {
	wall: Color("000")
	floor: Color("FF0")
	apple: Color("F00")
	snake: Color("0F0")
}

// params

width = 64
height = 48
food = 10
appleFood = 5

// options

options = Window {
	start: Centered.Button("Start") {
		onClick() {
			options.close()
			
			global field = Color[width, height].fill(floor)
			for(x = 0 ..< width) field[x, 0] = field[x, height - 1] = wall
			for(y = 0 ..< height) field[0, y] = field[width - 1, y] = wall
			placeApple()
			
			global score = 0, x = y = 2, dx = 1, dy = 0
			first = last = Pos(x, y)
			
			game.open()
		}
	}
	object: Column {
		Table(2) {
			Right.Text("Field width: ")
			TextField(#width, 10..100)
			Right.Text("Field height: ")
			TextField(#height, 10..100)
			Right.Text("Initial length: ")
			TextField(#food, 1..)
			Right.Text("Length increment: ")
			TextField(#appleFood, 1..)
			$start
		}
	}	
}

// main

Pos {
	Int x, y
	Pos next
	($x, $y) {}
}
Pos first, last

placeApple() {
	do {
		appleX = Int.random(0 ..< width)
		appleY = Int.random(0 ..< height)
		if(field[appleX, appleY] == floor) {
			field[appleX, appleY] = apple
			break
		}
	}
}

game = Hidden.Window("Game") {
	canvas: MaxIntScaled.Canvas {
		get originalWidth -> width
		get originalheight -> height
		render() {
			for(y = 0 ..< height, x = 0 ..< width) $drawRectangle(field[x, y], $xScale * x, $yScale * y, $xScale, $yScale)
		}
	}
	object: Column {
		Center.Text("Score: \(score)")
		$canvas
	}
	logicPerSecond: 6
	onKeyDown(key) {
		switch(key) {
			case A: dx = -1, dy = 0
			case D: dx = 1, dy = 0
			case W: dx = 0, dy = -1
			case S: dx = 0, dy = 1
		}
	}
	logic() {
		last.next = last = Pos(x, y)
		x += dx
		y += dy
		switch(field[x, y]) {
			case floor:
			case apple:
				food += appleFood
				score++
				placeApple()
			default:
				game.close()
				gameOver = Window("Game over!") {
					ok: Centered.Button("OK") {
						onClick() {
							gameOver.close()
							options.open()
						}
					}
					object: Column {
						Center.Text("You scored \score\ points.")
						$ok
					}
				}
		}
		field[x, y] = snake
		if(food > 0) {
			food--
		} else {
			field[first.x, first.y] = floor
			first = first.next
		}
	}
}

