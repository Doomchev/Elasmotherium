import Drawable;

class Tilemap extends Drawable {
  Int cellXQuantity, cellYQuantity, cellWidth, cellHeight;
  Array<Int> tiles;

  this(this.cellXQuantity, this.cellYQuantity, this.images, Int tileNumber = 0) {
    Int tilesQuantity = images.size;
    cellWidth = images[0].width;
    cellHeight = images[0].height;
    tiles = Array<Int>(cellXQuantity * cellYQuantity, tileNumber);
  }
	
	Int width() -> cellWidth * cellXQuantity;
	Int height() -> cellHeight * cellYQuantity;
  
  draw(Int x = 0, Int y = 0) {
    for(Int tileY = 0 ..< cellYQuantity)
			for(Int tileX = 0 ..< cellXQuantity)
				images[tiles[tileX + tileY * cellXQuantity]].draw(x + tileX * cellWidth, y + tileY * cellHeight);
  }
  
  Int at(Int cellX, Int cellY) -> tiles[cellX + cellY * cellXQuantity];
  at(Int cellX, Int cellY, Field tileNumber) tiles[cellX + cellY * cellXQuantity] = tileNumber;
  
  Int tileX(Int screenX) -> screenX / cellWidth;
  Int tileY(Int screenY) -> screenY / cellHeight;
}