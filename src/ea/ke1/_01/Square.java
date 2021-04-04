package ea.ke1._01;

import java.util.EnumSet;

/**
 * A single game board square with walls and type
 */
class Square {
  private SquareType type = SquareType.DEFAULT;
  private final Wall[] walls;

  /**
   * Creates a new square with given walls
   *
   * @param left   left wall
   * @param top    top wall
   * @param right  right wall
   * @param bottom bottom wall
   */
  public Square(Wall left, Wall top, Wall right, Wall bottom) {
    walls = new Wall[]{left, top, right, bottom};
  }

  /**
   * @param position side of the wall
   * @return wall object of the given side of the square
   */
  private Wall wallAt(WallPosition position) {
    return walls[position.ordinal()];
  }

  public boolean isTrap() {
    return type == SquareType.TRAP;
  }

  public boolean isDefault() {
    return type == SquareType.DEFAULT;
  }

  /**
   * @param position side of the wall
   * @return true if square as active wall on given side
   */
  public boolean hasWall(WallPosition position) {
    return wallAt(position).isActive();
  }

  /**
   * Activates wall on given side
   *
   * @param position side of the wall
   */
  public void setWall(WallPosition position) {
    walls[position.ordinal()].activate();
  }

  /**
   * Activates all walls around square
   */
  public void setAllWall() {
    for (Wall w : walls) {
      w.activate();
    }
  }

  public void setType(SquareType newType) {
    type = newType;
  }

  public void setTrap() {
    if (isDefault() || isTrap()) {
      type = SquareType.TRAP;
    } else {
      throw new IllegalArgumentException("Selected square is already Start or Finish");
    }
  }


  private void printVerticalWall(WallPosition pos) {
    if (hasWall(pos)) {
      BoxCharacter.PIPE.print();
    } else {
      BoxCharacter.NONE.print();
    }
  }

  private void printHorizontalWall(WallPosition pos) {
    if (hasWall(pos)) {
      BoxCharacter.DASH.print();
      BoxCharacter.DASH.print();
      BoxCharacter.DASH.print();
    } else {
      BoxCharacter.NONE.print();
      BoxCharacter.NONE.print();
      BoxCharacter.NONE.print();
    }
  }

  public void printLeftWall() {
    printVerticalWall(WallPosition.LEFT);
  }

  public void printRightWall() {
    printVerticalWall(WallPosition.RIGHT);
  }

  public void printTopWall() {
    printHorizontalWall(WallPosition.TOP);
  }

  public void printBottomWall() {
    printHorizontalWall(WallPosition.BOTTOM);
  }

  public BoxCharacter getTopLeftCorner() {
    EnumSet<BoxCharacter> composition = EnumSet.noneOf(BoxCharacter.class);
    if (hasWall(WallPosition.TOP)) {
      composition.add(BoxCharacter.HALF_DASH_RIGHT);
    }
    if (hasWall(WallPosition.LEFT)) {
      composition.add(BoxCharacter.HALF_PIPE_DOWN);
    }
    return BoxCharacter.merge(composition);
  }

  public BoxCharacter getTopRightCorner() {
    EnumSet<BoxCharacter> composition = EnumSet.noneOf(BoxCharacter.class);
    if (hasWall(WallPosition.TOP)) {
      composition.add(BoxCharacter.HALF_DASH_LEFT);
    }
    if (hasWall(WallPosition.RIGHT)) {
      composition.add(BoxCharacter.HALF_PIPE_DOWN);
    }
    return BoxCharacter.merge(composition);
  }

  public BoxCharacter getBottomRightCorner() {
    EnumSet<BoxCharacter> composition = EnumSet.noneOf(BoxCharacter.class);
    if (hasWall(WallPosition.BOTTOM)) {
      composition.add(BoxCharacter.HALF_DASH_LEFT);
    }
    if (hasWall(WallPosition.RIGHT)) {
      composition.add(BoxCharacter.HALF_PIPE_UP);
    }
    return BoxCharacter.merge(composition);
  }

  public BoxCharacter getBottomLeftCorner() {
    EnumSet<BoxCharacter> composition = EnumSet.noneOf(BoxCharacter.class);
    if (hasWall(WallPosition.BOTTOM)) {
      composition.add(BoxCharacter.HALF_DASH_RIGHT);
    }
    if (hasWall(WallPosition.LEFT)) {
      composition.add(BoxCharacter.HALF_PIPE_UP);
    }
    return BoxCharacter.merge(composition);
  }

  public void print() {
    System.out.print(type.string);
  }
}
