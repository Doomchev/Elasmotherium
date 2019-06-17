package examples.patterns;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static java.lang.Math.*;

public class Window {
    JFrame frame;
    JLabel label;
    Drawable object;
    int scale;

    public Window(String title, Drawable object) {
      this.object = object;
      frame = new JFrame(title);
    }
    
    public void open() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      label = new JLabel() {
        @Override
        public void paint(Graphics g) {
          object.draw(g, 0, 0, scale);
        }
      };
      label.setPreferredSize(new Dimension(object.getWidth() * scale
          , object.getHeight() * scale));
      frame.getContentPane().add(label, BorderLayout.CENTER);
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      frame.pack();
      frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height
          / 2 - frame.getSize().height / 2);
      frame.setVisible(true);
    }
    
    public Window intScaleMax() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Insets insets = toolkit.getScreenInsets(frame.getGraphicsConfiguration());
        int maxWidth = screenSize.width - (insets.left + insets.right);
        int maxHeight = screenSize.height - (insets.top + insets.bottom);
        scale = min(maxWidth / object.getWidth()
            , maxHeight / object.getHeight());
        return this;
    }
  }