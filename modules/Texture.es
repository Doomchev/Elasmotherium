class Texture {
  Int width();
  Int height();

	create(String fileName);
  
  Array<Image> cut(Int columnsQuantity, Int rowsQuantity = 1) {
    Int quantity = columnsQuantity * rowsQuantity;
    Int cellWidth = width / rowsQuantity;
    Int cellHeight = height / columnsQuantity;
    Array<Image> images = Array<Image>(quantity);
    Int index = 0;
    for(Int y = 0 ..< rowsQuantity) {
      Int yy = cellHeight * y;
      for(Int x = 0 ..< columnsQuantity) {
        Int xx = cellWidth * x;
        images[index] = Image(this, xx, yy, cellWidth, cellHeight);
      }
    }
    return images;
  }
  
  draw(Int targetX = (screenWidth - width) / 2, Int targetY = (screenHeight - height) / 2, Int targetWidth = width, Int targetHeight = height, Int sourceX = 0, Int sourceY = 0, Int sourceWidth = width, Int sourceHeight = height);
}