import Texture;
import Tilemap;

Int empty = 0;
Int white = 1;
Int black = 2;

Int cellsQuantity = 7;
Int pawnsQuantity = (cellsQuantity - 1) / 2;
Int blackStart = pawnsQuantity + 1;

Texture board = Texture("board.png");
TileMap tileMap = TileMap(cellsQuantity, 1, Texture("pawns.png").cut(3), empty);

init() {
	for(Int n = 0 ..< pawnsQuantity) {
    tileMap[n] = white;
    tileMap[blackStart + n] = black;
  }
	say("Вам нужно поменять черные и белые пешки местами.\nЧерные пешки ходят влево, белые - вправо.\n"
			+ "Пешка может пойти на одну клетку вперед\nили перепрыгнуть через следующую пешку\nна свободное поле."
			, "Правила игры");
}

init();

render() {
	board.draw();
	tileMap.draw();
}

onClick(Int x, Int y) {
  Int tileNum = bound(tileMap.tileX(x - tileMapX), 0, tileMap.cellXQuantity - 1);
  Int tile = tileMap[tileNum, 0];
  if(tile == white && tileNum < cellsQuantity - 1) {
    Field nextTile = tileMap[tileNum + 1, 0];
    if(nextTile == empty) {
      tileMap[tileNum, 0] = empty;
      tileMap[tileNum + 1, 0] = white;
    } else if(tileNum < cellsQuantity - 2 && tileMap[tileNum + 2, 0] == empty) {
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
  } else return;
  
  for(Int n = 0 ..< pawnsQuantity)
    if(tileMap[n, 0] != black || tileMap[n + blackStart, 0] != white) return;
  
  say("Вы выиграли!");
  init();
}