Layer {
  contents: []
  
  Void onFirstCollision(I32 x, I32 y, function) {
    for(object in this.contents) {
      if(object.onFirstCollision(x, y, function)) return true
    }
  }
  
  draw() {
    for(object in this.contents) object.draw()
  }  
}