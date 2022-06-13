import Image;

class TileMap {
  Int columnsQuantity, rowsQuantity, cellWidth, cellHeight;
  Array<Int> tiles;
	Array<Image> images;

  create(field.columnsQuantity, field.rowsQuantity, field.images, Int tileNumber) {
    Int tilesQuantity = images.size();
    cellWidth = images[0].width;
    cellHeight = images[0].height;
    tiles = Array<Int>(columnsQuantity * rowsQuantity);
  }
	
	Int width() -> cellWidth * columnsQuantity;
	Int height() -> cellHeight * rowsQuantity;
  
  draw(Int x = (screenWidth - width) / 2, Int y = (screenHeight - height) / 2) {
    for(Int tileY = 0 ..< rowsQuantity)
			for(Int tileX = 0 ..< columnsQuantity)
				images[tiles[tileX + tileY * columnsQuantity]].draw(x + tileX * cellWidth, y + tileY * cellHeight);
  }
  
  Int get(Int cellX, Int cellY) -> tiles[cellX + cellY * columnsQuantity];
  set(Int cellX, Int cellY, Int tileNumber) tiles[cellX + cellY * columnsQuantity] = tileNumber;
  
  Int tileX(Int screenX) -> screenX / cellWidth;
  Int tileY(Int screenY) -> screenY / cellHeight;
}