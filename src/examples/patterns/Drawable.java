package examples.patterns;

import java.awt.Graphics;

public interface Drawable {
  int getHeight();
  int getWidth();
  void draw(Graphics g, int x, int y);
  void draw(Graphics g, int x, int y, float scale);
}
