package base;

import vm.VMBase;
import vm.VMCommand;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
  private static JScrollPane codeView;
  private static int blockLine, blockColumn, blockWidth;
  
  public static String readText(String fileName) {
    try {
      return new String(Files.readAllBytes(Paths.get(fileName))
          , StandardCharsets.UTF_8);
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", "Cannot read " + fileName + ".");
    }
    return "";
  }

  public static void showDebugMessage(String title, String message
      , String text, int lineNumber) {
    blockLine = lineNumber; blockColumn = 0; blockWidth = 0;
    showDebugMessage(title, message, text);
  }
  
  public static void showDebugMessage(String title, String message
      , String text, int textStart, int textEnd) {
    blockLine = 1; blockColumn = 0;
    for(int index = 0; index < textStart; index++) {
      switch(text.charAt(index)) {
        case '\n':
          blockLine++;
          blockColumn = 0;
          break;
        case '\t':
          blockColumn += 2;
          break;
        default:
          blockColumn++;
      }
    }
    blockWidth = textEnd - textStart;
    showDebugMessage(title, message, text.replace("\t", "  "));
  }
  
  public static void showDebugMessage(String title, String message
      , String text) {
    if(code == null) {
      JFrame frame = new JFrame();
      frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      
      codeView = new JScrollPane();
      code = new JTextPane() {
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);

          Graphics2D g2 = (Graphics2D) g;

          g2.setColor(new Color(255, 0, 0, 128));
          
          FontMetrics fm = code.getFontMetrics(code.getFont());
          int symbolWidth = fm.charWidth('w');
          int symbolHeight = fm.getHeight();

          /*for(int xx = 0; xx < 100; xx++) {
            int x = symbolWidth * xx + 2;
            int width = 1;
            int y = symbolHeight * blockLine + 2;
            g2.fillRect(x, y, width, 4);
          }*/
          
          int x = symbolWidth * blockColumn + 3;
          int width = blockWidth == 0 ? getWidth() : symbolWidth * blockWidth;
          int y = symbolHeight * blockLine + 2;
          g2.fillRect(x, y, width, 4);

          g2.dispose();
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
          return getUI().getPreferredSize(this).width 
          <= getParent().getSize().width;
        }
      };
      code.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 13));
      
      codeView.setViewportView(code);
      frame.add(codeView);
      frame.setVisible(true);
    }
    
    println(message);
    code.setText(text);
    SwingUtilities.invokeLater(() -> {
      FontMetrics fm = code.getFontMetrics(code.getFont());
      codeView.getVerticalScrollBar().setValue(blockLine * fm.getHeight()
          - codeView.getHeight() / 2);
    });

    JOptionPane.showMessageDialog(null, message, title
        , JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }
}
