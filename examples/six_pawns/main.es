board = Texture("board.png")
tileMap = TileMap(7, 1, Texture("pawns.png").cut(3))

empty = 0
white = 1
black = 2

rules() {
	showMessage("Вам нужно поменять черные и белые пешки местами.\nЧерные пешки ходят влево, белые - вправо.\n"
			+ "Пешка может пойти на одну клетку вперед\nили перепрыгнуть через следующую пешку\nна свободное поле."
			, "Правила игры")
}

init() {
	for(n = 0 ..< tileMap.cellXQuantity) tileMap[n] = n <= 2 ? white : (n >= 4 ? black : empty)
}

init()
rules()

Window("Six pawns", tileMap) {
	render() {
		tileMapX = (wnd.width - tileMap.cellWidth * 7) / 2
		tileMapY = (wnd.height - tileMap.cellHeight) / 2 - 31
		boardX = (wnd.width - board.width) / 2
		boardY = (wnd.height - board.height) / 2 + 31
		
		board.draw(boardX, boardY)
		$tileMap.draw(tileMapX, tileMapY)
	}

	onClick(Int x, Int y) {
		tileNum = limit(tileMap.getTileX(x - tileMapX), 0, tileMap.cellXQuantity - 1)
		tile = tileMap.getTile(tileNum, 0)
		if(tile == white && tileNum < tileMap.cellXQuantity - 1) {
			nextTile = tileMap.getTile(tileNum + 1, 0)
			if(nextTile == empty) {
				tileMap.setTile(tileNum, 0, empty)
				tileMap.setTile(tileNum + 1, 0, tile)
			} else if(tileNum < $cellXQuantity - 2 && tileMap.getTile(tileNum + 2, 0) == empty) {
				tileMap.setTile(tileNum, 0, empty)
				tileMap.setTile(tileNum + 2, 0, tile)
			}
		} else if(tile == black && tileNum > 0) {
			prevTile = tileMap.getTile(tileNum - 1, 0)
			if(prevTile == empty) {
				tileMap.setTile(tileNum, 0, empty)
				tileMap.setTile(tileNum - 1, 0, tile)
			} else if(tileNum > 1 && tileMap.getTile(tileNum - 2, 0) == empty) {
				tileMap.setTile(tileNum, 0, empty)
				tileMap.setTile(tileNum - 2, 0, tile)
			}
		}
		
		if(tileMap.getTile(0, 0) == white) return
		for(n = 1 ..< $cellXQuantity) {
			if(tileMap.getTile(n - 1, 0) == (tileMap.getTile(n, 0) == white ? black : white)) return
		}
		
		showMessage("Вы выиграли!")
		init()
		rules()
	}
}