class Image {
  Texture texture;
  Int x, y, width, height;

  create(field.texture, field.x = 0, field.y = 0, field.width = texture.width, field.height = texture.height);
  
  draw(Int x = (screenWidth - this.width) / 2, Int y = (screenHeight - this.height) / 2, Int w = this.width, Int h = this.height) {
    texture.draw(x, y, w, h, this.x, this.y, this.width, this.height);
  }
}
