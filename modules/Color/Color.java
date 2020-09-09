package modules.Color;

public class Color {
  public static int create(int red, int green, int blue, int alpha) {
    return red + (green << 8) + (blue << 16) + (alpha << 24);
  }
  
  public static int create(int red, int green, int blue) {
    return create(red, green, blue, 255);
  }
  
  public static int create(int intensity, int alpha) {
    return create(intensity, intensity, intensity, alpha);
  }
  
  public static int create(int intensity) {
    return create(intensity, intensity, intensity, 255);
  }
}
