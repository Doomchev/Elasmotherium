import Shape;
import Sprite;
import Rectangle;

class Camera<Element extends Sprite> extends Sprite {
  Rectangle viewport;
  Float xk, yk, vdx, vdy;
  List<Layer<Element>> layers = new List();
  
  draw() {
    update();
    for(layer: layers) layer.draw(this);
  }
  
  draw(Sprite sprite) {
    sprite.draw((sprite.x + vdx) * xk, (sprite.y + vdy) * yk, sprite.width * xk, sprite.height * yk);
  }
  
  update() {
    xk = width / viewport.width;
    yk = height / viewport.height;
    vdx = x - viewport.x;
    vdy = y - viewport.y;
  }
}