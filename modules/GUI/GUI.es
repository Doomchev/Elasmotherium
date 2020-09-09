IntSize {
  Int width, height
  ($width, $height)
}

GUIElement {
  get Int width -> $size.width
  get Int height -> $size.height
  get IntSize size
}

GUISequence extends GUIElement {
  Color backgroundColor
  innerlist List<GUIElement> elements
}

Row extends GUISequence {
  get IntSize size {
    width = 0
    height = 0
    for(element in elements) {
      size = element.size
      width += size.width
      height = max(height, size.height)
    }
    return(IntSize(width, height))
  }
  
  draw(x = 0, y = 0) {
    for(element in elements) {
      element.draw(x, y)
      x += element.width
    }
  }
}

Column extends GUISequence {
  get IntSize size {
    width = 0
    height = 0
    for(element in elements) {
      size = element.size
      width = max(width, size.width)
      height += size.height
    }
    return(IntSize(width, height))
  }
  
  draw(x = 0, y = 0) {
    for(element in elements) {
      element.draw(x, y)
      y += element.height
    }
  }
}

ListView<ElementType> extends GUIElement {
  List<ElementType> list
}

ColumnView extends ListView {


   get IntSize size {
    width = 0
    height = 0
    for(element in elements) {
      size = element.size
      width = max(width, size.width)
      height += size.height
    }
    return(IntSize(width, height))
  } 
}