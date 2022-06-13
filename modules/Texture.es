class Texture {
  Int width();
  Int height();

	create(String fileName);
  
  Array<Image> cut(Int columnsQuantity, Int rowsQuantity = 1) {
    Int quantity = columnsQuantity * rowsQuantity;
    Float cellWidth = width / rowsQuantity;
    Float cellHeight = height / columnsQuantity;
    Array<Image> images = Array<Image>(quantity);
    Int index = 0;
    for(Int y = 0 ..< rowsQuantity) {
      Float yy = cellHeight * y;
      for(Int x = 0 ..< columnsQuantity) {
        Float xx = cellWidth * x;
        images[index] = Image(this, xx, yy, cellWidth, cellHeight);
      }
    }
    return images;
  }
  
  draw(Int targetX = 0, Int targetY = 0, Int targetWidth = width, Int targetHeight = height, Int sourceX = 0, Int sourceY = 0, Int sourceWidth = width, Int sourceHeight = height);
}