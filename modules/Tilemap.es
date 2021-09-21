class TileMap {
  Int cellXQuantity, cellYQuantity, cellWidth, cellHeight;
  Array<Int> tiles;
	Array<Image> images;

  create(field.cellXQuantity, field.cellYQuantity, field.images, Int tileNumber) {
    Int tilesQuantity = images.size;
    cellWidth = images[0].width;
    cellHeight = images[0].height;
    tiles = Array<Int>(cellXQuantity * cellYQuantity, tileNumber);
  }
	
	Int width() -> cellWidth * cellXQuantity;
	Int height() -> cellHeight * cellYQuantity;
  
  draw(Int x = (screenWidth() - width()) / 2, Int y = (screenHeight() - height()) / 2) {
    for(Int tileY from 0 until cellYQuantity)
			for(Int tileX from 0 until cellXQuantity)
				images[tiles[tileX + tileY * cellXQuantity]].draw(x + tileX * cellWidth, y + tileY * cellHeight);
  }
  
  Int getAtIndex(Int cellX, Int cellY) -> tiles[cellX + cellY * cellXQuantity];
  setAtIndex(Int cellX, Int cellY, Int tileNumber) tiles[cellX + cellY * cellXQuantity] = tileNumber;
  
  Int tileX(Int screenX) -> screenX / cellWidth;
  Int tileY(Int screenY) -> screenY / cellHeight;
}