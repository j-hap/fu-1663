package ea.ke1._01;

import java.util.EnumSet;

/**
 * Characters for printing boxes on text-out
 */
enum BoxCharacter {
  NONE(0x0, ' '),
  HALF_PIPE_UP(0x1, '╵'),
  HALF_DASH_RIGHT(0x2, '╶'),
  HALF_PIPE_DOWN(0x4, '╷'),
  HALF_DASH_LEFT(0x8, '╴'),
  PIPE(HALF_PIPE_UP.value | HALF_PIPE_DOWN.value, '│'),
  DASH(HALF_DASH_LEFT.value | HALF_DASH_RIGHT.value, '─'),
  TOP_LEFT_CORNER(HALF_PIPE_DOWN.value | HALF_DASH_RIGHT.value, '┌'),
  TOP_RIGHT_CORNER(HALF_PIPE_DOWN.value | HALF_DASH_LEFT.value, '┐'),
  BOTTOM_LEFT_CORNER(HALF_PIPE_UP.value | HALF_DASH_RIGHT.value, '└'),
  BOTTOM_RIGHT_CORNER(HALF_PIPE_UP.value | HALF_DASH_LEFT.value, '┘'),
  TOP_T(TOP_RIGHT_CORNER.value | HALF_DASH_RIGHT.value, '┬'),
  LEFT_T(TOP_LEFT_CORNER.value | HALF_PIPE_UP.value, '├'),
  RIGHT_T(TOP_RIGHT_CORNER.value | HALF_PIPE_UP.value, '┤'),
  BOTTOM_T(BOTTOM_RIGHT_CORNER.value | HALF_DASH_RIGHT.value, '┴'),
  INTERSECTION(TOP_T.value | BOTTOM_T.value, '┼');

  private final int value;
  private final char character;

  BoxCharacter(int value, char character) {
    this.value = value;
    this.character = character;
  }

  public static BoxCharacter fromValue(int value) {
    for (BoxCharacter e : values()) {
      if (value == e.value) {
        return e;
      }
    }
    return NONE;
  }

  public BoxCharacter merge(BoxCharacter other) {
    if (other == null) {
      return this;
    }
    return fromValue(this.value | other.value);
  }

  public void print() {
    System.out.print(this.character);
  }

  public static BoxCharacter merge(EnumSet<BoxCharacter> elements) {
    int result = 0;
    for (BoxCharacter e : elements) {
      result |= e.value;
    }
    return fromValue(result);
  }
}
