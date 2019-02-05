
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowImage {
  static Graphics g;
  
  static class Window {
    JFrame frame;
    JLabel label;
    DrawableRectange object;
    
    public void render(DrawableRectange object) {
      frame = new JFrame("Application");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.object = object;
      label = new JLabel() {
        @Override
        public void paint(Graphics g2) {
          g = g2;
          render();
        }
      };
      label.setPreferredSize(new Dimension(object.getWidth(), object.getHeight()));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      frame.setLocationRelativeTo(null);
      frame.pack();
      frame.setVisible(true);
    }
    
    void render() {
      object.draw(0, 0);
    }
  }
  
  static abstract class DrawableRectange {
    abstract int getWidth();
    abstract int getHeight();
    abstract void draw(int x, int y);
  }
  
  static abstract class Function {
    abstract int get(int x, int y);
  }
  
  static class Texture extends DrawableRectange {
    BufferedImage image;
    
    Texture(int width, int height) {
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    
    Texture(int width, int height, Function function) {
      this(width, height);
      for(int y = 0; y < height; y++) {
        for(int x = 0; x < width; x++) {
          put(x, y, function.get(x, y));
        }
      }
    }
    
    final void put(int x, int y, int c) {
      image.setRGB(x, y, c);
    }

    @Override
    int getWidth() {
      return image.getWidth();
    }

    @Override
    int getHeight() {
      return image.getHeight();
    }

    @Override
    void draw(int x, int y) {
      g.drawImage(image, x, y, null);
    }
  }
  
  static int createColor(int r) {
    int b = r;
    int c = r;
    int alpha = 255;
    return r | (b << 8) | (c << 16) | (alpha << 24);
  }
  
  public static void main(String[] args) {
    (new Window()).render(new Texture(256, 256, new Function() {
      @Override
      int get(int x, int y) {
        return createColor((((x >> 4) + (y >> 4)) & 1) != 0 ? 255 : 0);
      }
    }));
  }
}
