class Image {
  Int x, y, width, height;

  this(this.texture, this.x = 0, this.y = 0, this.width = this.texture.width, this.height = this.texture.height);
  
  draw(Int x = (screenWidth() - width) / 2, Int y = (screenHeight() - height) / 2, Int width = this.width, Int height = this.height) {
    texture.draw(x, y, width, height, this.x, this.y, this.width, this.height);
  }
}
