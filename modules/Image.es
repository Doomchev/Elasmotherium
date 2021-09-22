class Image {
  Texture texture;
  Int x, y, width, height;

  create(field.texture, field.x = 0, field.y = 0, field.width = this.texture.width, field.height = this.texture.height);
  
  draw(Int x = (screenWidth - width) / 2, Int y = (screenHeight - height) / 2, Int width = this.width, Int height = this.height) {
    texture.draw(x, y, width, height, this.x, this.y, this.width, this.height);
  }
}
