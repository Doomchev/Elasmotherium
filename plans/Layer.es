Layer {
  contents: []
  
  Question onFirstCollision(Int x, Int y, function) {
    for(object in this.contents)
      if(object.onFirstCollision(x, y, function)) return yes;
    return no;
  }
  
  draw()
    for(each object in contents) object.draw()
}