import Texture;
import Tilemap;
import Window;

Int empty = 0;
Int white = 1;
Int black = 2;

Texture board = Texture("board.png");
TileMap tileMap = TileMap(7, 1, Texture("pawns.png").cut(3));

init() {
	with tileMap;
	for(Int n = 0 .. cellXQuantity) this[n] = n <= 2 ? white : (n >= 4 ? black : empty);
	tell("Вам нужно поменять черные и белые пешки местами.\nЧерные пешки ходят влево, белые - вправо.\n"
			+ "Пешка может пойти на одну клетку вперед\nили перепрыгнуть через следующую пешку\nна свободное поле."
			, "Правила игры");
}

init();

class Window2 extends Window {
	with tileMap;
	
	render() {
		board.draw((width - board.width) / 2, (height - board.height) / 2 + 31);
		draw((width - cellWidth * 7) / 2, (height - cellHeight) / 2 - 31);
	}

	onClick(Int x, Int y) {
		Int tileNum = limit(tileX(x - tileMapX), 0, cellXQuantity - 1);
		Int tile = this[tileNum, 0];
		if(tile == white && tileNum < cellXQuantity - 1) {
			Field nextTile = this[tileNum + 1, 0];
			if(nextTile == empty) {
				this[tileNum, 0] = empty;
				this[tileNum + 1, 0] = white;
			} else if(tileNum < cellXQuantity - 2 && this[tileNum + 2, 0] == empty) {
				this[tileNum, 0] = empty;
				this[tileNum + 2, 0] = white;
			}
		} else if(tile == black && tileNum > 0) {
			Int prevTile = this[tileNum - 1, 0];
			if(prevTile == empty) {
				this[tileNum, 0] = empty;
				this[tileNum - 1, 0] = black;
			} else if(tileNum > 1 && this[tileNum - 2, 0] == empty) {
				this[tileNum, 0] = empty;
				this[tileNum - 2, 0] = black;
			}
		}
		
		if(this[0, 0] == white) return;
		for(Int n = 1 ..< cellXQuantity)
			if(this[n - 1, 0] == (this[n, 0] == white ? black : white)) return;
		
		tell("Вы выиграли!");
		init();
	}
}

Window2("Six pawns", tileMap);
