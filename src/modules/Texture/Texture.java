package modules.texture;

import examples.patterns.Drawable;
import examples.patterns.Int32_Int_Int_;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Texture extends BufferedImage implements Drawable {
  Texture(int width, int height) {
    super(width, height, BufferedImage.TYPE_INT_RGB);
  }

  Texture(int width, int height, Int32_Int_Int_ function) {
    this(width, height);
    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
        put(x, y, function.get(x, y));
      }
    }
  }

  final void put(int x, int y, int c) {
    setRGB(x, y, c);
  }

  @Override
  public void draw(Graphics g, int x, int y) {
    g.drawImage(this, x, y, null);
  }

  @Override
  public void draw(Graphics g, int x, int y, float scale) {
    g.drawImage(this, x, y, (int) (getWidth() * scale)
        , (int) (getHeight() * scale), null);
  }
}