package examples.patterns;

public class Color {
  public static int create_(int r) {
    return create_(r, r, r, 255);
  }
  public static int create_(int r, int g) {
    return create_(r, g, r, 255);
  }
  public static int create_(int r, int g, int b) {
    return create_(r, g, b, 255);
  }
  public static int create_(int r, int g, int b, int alpha) {
    return r + (g << 8) + (b << 16) + (alpha << 24);
  }
}
