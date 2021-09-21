Layer {
  contents: []
  
  Question onFirstCollision(Int x, Int y, function) {
    for(object: this.contents)
      if(object.onFirstCollision(x, y, function)) return yes;
    return no;
  }
  
  draw()
    for(object: contents) object.draw()
}