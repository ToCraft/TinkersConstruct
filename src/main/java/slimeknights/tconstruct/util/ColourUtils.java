package slimeknights.tconstruct.util;

import java.awt.*;

public class ColourUtils {

  public static int getA(int color) {
    return new Color(color).getAlpha();
  }

  public static int getR(int color) {
    return new Color(color).getRed();
  }

  public static int getG(int color) {
    return new Color(color).getGreen();
  }

  public static int getB(int color) {
    return new Color(color).getBlue();
  }

  public static int combine(int a, int b, int g, int r) {
    return ((a & 0xFF) << 24) |
      ((r & 0xFF) << 16) |
      ((g & 0xFF) << 8) |
      ((b & 0xFF));
  }
}
