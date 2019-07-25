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
  public static int r_(int color) {
    return color & 0x000000FF;
  }
  public static int r_(int color, int r) {
    return (color & 0xFFFFFF00) | r;
  }
  public static int g_(int color) {
    return color & 0x0000FF00;
  }
  public static int g_(int color, int g) {
    return (color & 0xFFFF00FF) | (g << 8);
  }
  public static int b_(int color) {
    return color & 0x00FF0000;
  }
  public static int b_(int color, int b) {
    return (color & 0xFF00FFFF) | (b << 16);
  }
  public static int alpha_(int color) {
    return color & 0xFF000000;
  }
  public static int alpha_(int color, int alpha) {
    return (color & 0x00FFFFFF) | (alpha << 24);
  }
}
