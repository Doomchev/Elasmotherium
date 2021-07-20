class Texture extends Drawable {
	this(Int width, Int height);
	
	Array<Image> cut(Int xQuantity, Int yQuantity = 1, Int cellWidth = width / xQuantity, Int cellHeight = height / yQuantity
			, Int x = 0, Int y = 0, Int cellSpacing = 0) {
		Int xk = cellSpacing + cellWidth;
		Int yk = cellSpacing + cellHeight;
		Array<Image> images = Array<Image>(xQuantity * yQuantity);
		for(Int j = 0 ..< yQuantity)
			for(Int i = 0 ..< xQuantity)
				images[i + j * xQuantity] = Image(this, x + i * xk, y + j * yk, cellWidth, cellHeight);
		return images;
	}
}