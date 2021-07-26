class Image {
  this(this.texture, this.x = 0, this.y = 0, this.width = this.texture.width, this.height = this.texture.height);
  
  draw(Int x = 0, Int y = 0, Int width = this.width, Int height = this.height) {
    texture.draw(x, y, width, height, this.x, this.y, this.width, this.height);
  }
}
