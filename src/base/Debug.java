package base;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import vm.VMBase;
import vm.VMCommand;

public class Debug extends StringFunctions {
  public static LineReader currentLineReader;
  public static SymbolReader currentSymbolReader;
  public static StringBuilder subIndent = new StringBuilder();
  public static final boolean log = true;

  public String getClassName() {
    return getClass().getSimpleName();
  }

  public static void appendLog(VMCommand command) {
    if(log) println(subIndent + command.toString());
    VMBase.append(command);
  }
  
  public static void error(String title, String message) {
    System.out.println(title);
    System.out.println(message);
    System.exit(1);
  }
  
  public static void println(String string) {
    System.out.println(string);
  }
  
  public static void printChapter(String string) {
    final int length = 70, half = length / 2 - 1;
    System.out.println(createString('=', length));
    System.out.println(createString('=', half - string.length() / 2) + " "
         + string + " " + createString('=', half - (string.length() + 1) / 2));
    System.out.println(createString('=', length));
  }

  @Override
  public String toString() {
    return getClassName();
  }
  
  // debugger window
  
  private static JTextPane code = null;
  private static int blockLine, blockColumn, blockWidth;
  
  public static void showDebugMessage(String title, String message
      , Module module, int textStart, int textEnd) {
    String text = module.readText().replace("\t", "  ");
    blockLine = 1; blockColumn = 0; blockWidth = 0;
    for(int index = 0; index < textStart; index++) {
      if(text.charAt(index) == '\n') {
        blockLine++;
        blockColumn = 0;
      } else {
        blockColumn++;
      }
    }
    blockWidth = textEnd - textStart;
    
    if(code == null) {
      JFrame frame = new JFrame();
      frame.setVisible(true);
      frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      code = new JTextPane() {
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);

          Graphics2D g2 = (Graphics2D) g;

          g2.setColor(new Color(255, 0, 0, 128));

          FontMetrics fm = getFontMetrics(getFont());
          int x = fm.charWidth('w') * blockColumn;
          int width = fm.charWidth('w') * blockWidth;
          int y = fm.getHeight() * blockLine + 2;
          g2.fillRect(x, y, width, 4);

          g2.dispose();
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
          return getUI().getPreferredSize(this).width 
          <= getParent().getSize().width;
        }
      };
      code.setFont(new java.awt.Font("Monospaced", 0, 13));
      frame.add(code);
    }
    
    code.setText(text);
    
    println(message);
    JOptionPane.showMessageDialog(null, message, title
        , JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }
}
