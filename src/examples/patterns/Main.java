package examples.patterns;

public class Main {
  public static void main(String[] args) {
    new Window("Chessboard", new Texture(128, 128, new Int32_Int_Int_() {
      @Override
      public int get(int x, int y) {
        return Color.create_(((x >> 4) + (y >> 4) & 1) != 0 ? 255 : 128);
      }
    })).intScaleMax().open();
  }
}
