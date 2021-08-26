List<Image> Image.map(String fileName, Int cellXQuantity = 1, Int cellYQuantity = 1) {
	Texture texture = Texture.load(fileName, 1);
	cellWidth = texture.width / cellXQuantity;
	cellHeight = texture.height / cellYQuantity;
	imageList = List<Image>(cellXQuantity * cellYQuantity);
	index = 0;
	for(y from 0 until cellYQuantity)
		for(x from 0 until cellXQuantity) {
			imageList[index] = Image(texture, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
			index++;
		}
	return imageList;
}