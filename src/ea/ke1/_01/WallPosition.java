package ea.ke1._01;

import java.util.Random;

enum WallPosition {
  LEFT, TOP, RIGHT, BOTTOM;

  public static WallPosition getRandom() {
    Random rand = new Random();
    return WallPosition.values()[rand.nextInt(WallPosition.values().length)];
  }
}
