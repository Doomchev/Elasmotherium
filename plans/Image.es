List<Image> Image.map(String fileName, Int cellXQuantity = 1, Int cellYQuantity = 1) {
	Texture texture = Texture.load(fileName, 1);
	Int cellWidth = texture.width / cellXQuantity;
	Int cellHeight = texture.height / cellYQuantity;
	List<Image> imageList = List(cellXQuantity * cellYQuantity);
	Int index = 0;
	for(Int y from 0 until cellYQuantity)
		for(Int x from 0 until cellXQuantity) {
			imageList[index] = Image(texture, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
			index++;
		}
	return imageList;
}