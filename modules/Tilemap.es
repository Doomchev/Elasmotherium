class Tilemap {
  Int cellXQuantity, cellYQuantity, cellWidth, cellHeight;
  Array<Int> tiles;
	Array<Image> images;

  create(this.cellXQuantity, this.cellYQuantity, this.images, Int tileNumber) {
    Int tilesQuantity = images.size();
    cellWidth = images[0].width;
    cellHeight = images[0].height;
    tiles = Array<Int>(cellXQuantity * cellYQuantity, tileNumber);
  }
	
	Int width() -> cellWidth * cellXQuantity;
	Int height() -> cellHeight * cellYQuantity;
  
  draw(Int x = (screenWidth() - width()) / 2, Int y = (screenHeight() - height()) / 2) {
    for(Int tileY = 0 ..< cellYQuantity)
			for(Int tileX = 0 ..< cellXQuantity)
				images[tiles[tileX + tileY * cellXQuantity]].draw(x + tileX * cellWidth, y + tileY * cellHeight);
  }
  
  Int getAtIndex(Int cellX, Int cellY) -> tiles[cellX + cellY * cellXQuantity];
  setAtIndex(Int cellX, Int cellY, Int tileNumber) tiles[cellX + cellY * cellXQuantity] = tileNumber;
  
  Int tileX(Int screenX) -> screenX / cellWidth;
  Int tileY(Int screenY) -> screenY / cellHeight;
}