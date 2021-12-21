class Texture {
  Int width();
  Int height();

	create(String fileName);
  
  draw(Int targetX = (screenWidth - width) / 2, Int targetY = (screenHeight - height) / 2, Int targetWidth = width, Int targetHeight = height, Int sourceX = 0, Int sourceY = 0, Int sourceWidth = width, Int sourceHeight = height);
}