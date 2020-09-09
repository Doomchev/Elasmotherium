import Shape
import Sprite

Camera<Element extends Sprite> extends Sprite {
  Shape viewport
  Float xk, yk, vdx, vdy
  List<Layer<Element>> layers
  
  draw() {
    update()
    for(layer in layers) layer.draw(this)
  }
  
  draw(Sprite sprite) {
    sprite.draw((sprite.x + $vdx) * $xk, (sprite.y + $vdy) * $yk, sprite.width * $xk
        , sprite.height * $yk)
  }
  
  update() {
    $xk = $width / $viewport.width
    $yk = $height / $viewport.height
    $vdx = $x - $viewport.x
    $vdy = $y - $viewport.y
  }
}