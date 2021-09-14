class Texture {
  Int width();
  Int height();

	create(String fileName);
	
	/*Array<Image> cut(Int xQuantity = 1, Int yQuantity = 1, Int cellWidth = width / xQuantity, Int cellHeight = height / yQuantity, Int x = 0, Int y = 0, Int cellSpacing = 0) {
		Int xk = cellSpacing + cellWidth;
		Int yk = cellSpacing + cellHeight;
		Array<Image> images = Array<Image>(xQuantity * yQuantity);
		for(Int j from 0 until yQuantity)
			for(Int i from 0 until xQuantity)
				images[i + j * xQuantity] = Image(this, x + i * xk, y + j * yk, cellWidth, cellHeight);
		return images;
	}*/
  draw() {
    draw((screenWidth() - width()) / 2, (screenHeight() - height()) / 2, width(), height(), 0, 0, width(), height());
  }
  
  draw(Int targetX = (screenWidth() - targetWidth) / 2, Int targetY = (screenHeight() - targetHeight) / 2, Int targetWidth = width(), Int targetHeight = height(), Int sourceX = 0, Int sourceY = 0, Int sourceWidth = width(), Int sourceHeight = height());
}