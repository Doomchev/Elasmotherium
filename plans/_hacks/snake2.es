import Wsad

upKey = Key("W")
downKey = Key("S")
leftKey = Key("A")
rightKey = Key("D")

import Colors
  import Color
    class Color extends I32
      create
  black = Color("0")
  grey = Color("8")
  red = Color("F00")

import TileMap
  class TileMap<TileType>
    

import Window
  class Window

enum Tiles<Color>
	wall: black
	floor: grey
	apple: red
	snake: green

// params

width = 64
height = 48
food = 10
appleFood = 5

TileMap gameField
tileSet = ColorTileSet<Tiles>()
Int score, x, y, dx, dy

// options

options = Window
	start = Centered.Button("Start") {
		onClick() {
			options.close()
			
			gameField = TileMap<Tiles>(width, height, tileSet, floor)
			for(x = 0 ..< width) gameField(x, 0) = gameField(x, height - 1) = wall
			for(y = 0 ..< height) gameField(0, y) = gameField(width - 1, y) = wall
			placeApple()
			
			global score = 0, x = y = 2, dx = 1, dy = 0
			first = last = Pos(x, y)
			
			game.open()
		}
	}
	object = Column
		Table(2)
			Right.Text("Field width: ")
			TextField(reference width, 10 ..= 100)
			Right.Text("Field height: ")
			TextField(reference height, 10 ..= 100)
			Right.Text("Initial length: ")
			TextField(reference food, 1)
			Right.Text("Length increment: ")
			TextField(reference appleFood, 1)
			$start
		}
	}	
}

// main

Pos
	Pos next
	create(Int field x, Int field y)
  
Pos first, last

placeApple()
	repeat
		appleX = Int.random(0 ..< width)
		appleY = Int.random(0 ..< height)
		if(gameField(appleX, appleY) == floor)
			gameField(appleX, appleY) = apple
			break

game = Hidden.Game.Window("Color snake") {
	hud = Column
		Center.Text("Score: \(score)")
	logicPerSecond = 6
	onKeyDown(key)
		switch(key)
			case upKey
        dx = -1, dy = 0
			case rightKey
        dx = 1, dy = 0
			case leftKey
        dx = 0, dy = -1
			case downKey
        dx = 0, dy = 1
	logic()
		last.next = last = Pos(x, y)
		x += dx
		y += dy
		switch(gameField(x, y)) {
			case floor
			case apple
				food += appleFood
				score++
				placeApple()
			default:
				game.close()
				gameOver = Window("Game over!")
					gui = Column
						Center.Text("You scored \score\ points.")
            Centered.Button("OK")
              onClick()
                gameOver.close()
                game.close()
                options.open()
		gameField(x, y) = snake
		if(food > 0)
			food--
		else
			gameField(first.x, first.y) = floor
			first = first.next