import Texture;
import Tilemap;
import Window;

Int empty = 0;
Int white = 1;
Int black = 2;

Texture board = Texture("board.png");
TileMap tileMap = TileMap(7, 1, Texture("pawns.png").cut(3));

init() {
	for(Int n = 0 ..< tileMap.cellXQuantity) tileMap[n] = n <= 2 ? white : (n >= 4 ? black : empty);
	tell("Вам нужно поменять черные и белые пешки местами.\nЧерные пешки ходят влево, белые - вправо.\n"
			+ "Пешка может пойти на одну клетку вперед\nили перепрыгнуть через следующую пешку\nна свободное поле."
			, "Правила игры");
}

init();

render() {
	board.drawAtCenter();
	tileMap.drawAtCenter();
}

onClick(Int x, Int y) {
  Int tileNum = limit(tileMap.tileX(x - tileMapX), 0, tileMap.cellXQuantity - 1);
  Int tile = tileMap[tileNum, 0];
  if(tile == white && tileNum < tileMap.cellXQuantity - 1) {
    Field nextTile = tileMap[tileNum + 1, 0];
    if(nextTile == empty) {
      tileMap[tileNum, 0] = empty;
      tileMap[tileNum + 1, 0] = white;
    } else if(tileNum < tileMap.cellXQuantity - 2 && tileMap[tileNum + 2, 0] == empty) {
      tileMap[tileNum, 0] = empty;
      tileMap[tileNum + 2, 0] = white;
    }
  } else if(tile == black && tileNum > 0) {
    Int prevTile = tileMap[tileNum - 1, 0];
    if(prevTile == empty) {
      tileMap[tileNum, 0] = empty;
      tileMap[tileNum - 1, 0] = black;
    } else if(tileNum > 1 && tileMap[tileNum - 2, 0] == empty) {
      tileMap[tileNum, 0] = empty;
      tileMap[tileNum - 2, 0] = black;
    }
  }
  
  if(tileMap[0, 0] == white) return;
  for(Int n = 1 ..< tileMap.cellXQuantity)
    if(tileMap[n - 1, 0] == (tileMap[n, 0] == white ? black : white)) return;
  
  tell("Вы выиграли!");
  init();
}