import Texture;
import Tilemap;
import Math;

Int empty = 0;
Int white = 1;
Int black = 2;

Int cellsQuantity = 7;
Int pawnsQuantity = (cellsQuantity - 1) / 2;
Int blackStart = pawnsQuantity + 1;

Texture board = Texture("board.png");
TileMap tileMap = TileMap(cellsQuantity, 1, Texture("pawns.png").cut(3, 1), empty);

init() {
	for(Int n = 0 ..< pawnsQuantity) {
    tileMap.set(n, 0, white);
    tileMap.set(blackStart + n, 0, black);
  }
	say("Вам нужно поменять черные и белые пешки местами.\nЧерные пешки ходят влево, белые - вправо.\n"
			+ "Пешка может пойти на одну клетку вперед\nили перепрыгнуть через следующую пешку\nна свободное поле.");
}

init();

render() {
	board.draw();
	tileMap.draw();
}

onClick(Int x, Int y) {
  Int tileNum = bound(tileMap.tileX(x), 0, tileMap.columnsQuantity - 1);
  Int tile = tileMap.get(tileNum, 0);
  if(tile == white && tileNum < cellsQuantity - 1) {
    Int nextTile = tileMap.get(tileNum + 1, 0);
    if(nextTile == empty) {
      tileMap.set(tileNum, 0, empty);
      tileMap.set(tileNum + 1, 0, white);
    } else if(tileNum < cellsQuantity - 2 && tileMap.get(tileNum + 2, 0) == empty) {
      tileMap.set(tileNum, 0, empty);
      tileMap.set(tileNum + 2, 0, white);
    }
  } else if(tile == black && tileNum > 0) {
    Int prevTile = tileMap.get(tileNum - 1, 0);
    if(prevTile == empty) {
      tileMap.set(tileNum, 0, empty);
      tileMap.set(tileNum - 1, 0, black);
    } else if(tileNum > 1 && tileMap.get(tileNum - 2, 0) == empty) {
      tileMap.set(tileNum, 0, empty);
      tileMap.set(tileNum - 2, 0, black);
    }
  } else return;
  
  for(Int n = 0 ..< pawnsQuantity)
    if(tileMap.get(n, 0) != black || tileMap.get(n + blackStart, 0) != white) return;
  
  say("Вы выиграли!");
  init();
}