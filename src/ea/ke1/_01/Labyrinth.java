package ea.ke1._01;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

/**
 * A rectangular game board class
 */
public class Labyrinth {
  private final int nColumns;
  private final int nRows;
  private final Square[][] squares;
  private Square currentStart = null;
  private Square currentFinish = null;

  /**
   * @param nRows    Number of squares in each column of the labyrinth
   * @param nColumns Number of squares in each row of the labyrinth
   */
  public Labyrinth(int nRows, int nColumns) {
    // additional rows and cols for outer border
    if (nRows * nColumns == 1) {
      throw new IllegalArgumentException("Number of squares must be > 1");
    }
    this.nRows = nRows;
    this.nColumns = nColumns;
    squares = new Square[nRows][nColumns];
    initSquares();
    addOuterBorder();
    // default start and finish
    setStart(0,0);
    setFinish(nRows-1,nColumns-1);
  }

  /**
   * Creates all squares and assigns shared wall objects
   */
  private void initSquares() {
    Wall[][] verticalWalls = new Wall[nRows][nColumns + 1];
    for (Wall[] row : verticalWalls) {
      Arrays.setAll(row, i -> new Wall());
    }
    Wall[][] horizontalWalls = new Wall[nRows + 1][nColumns];
    for (Wall[] row : horizontalWalls) {
      Arrays.setAll(row, i -> new Wall());
    }

    for (int iRow = 0; iRow < nRows; ++iRow) {
      for (int iCol = 0; iCol < nColumns; ++iCol) {
        squares[iRow][iCol] = new Square(
                verticalWalls[iRow][iCol],
                horizontalWalls[iRow][iCol],
                verticalWalls[iRow][iCol + 1],
                horizontalWalls[iRow + 1][iCol]);
      }
    }
  }

  /**
   * Adds mandatory outer boarder to board
   */
  private void addOuterBorder() {
    for (Square s : squares[0]) {
      s.setWall(WallPosition.TOP);
    }
    for (Square s : squares[nRows - 1]) {
      s.setWall(WallPosition.BOTTOM);
    }
    for (Square[] s : squares) {
      s[0].setWall(WallPosition.LEFT);
      s[nColumns - 1].setWall(WallPosition.RIGHT);
    }
  }

  /**
   * @param iRow    row index for square extraction
   * @param iColumn column index for square extraction
   * @return A Square object at the specified coordinates
   */
  private Square getSquare(int iRow, int iColumn) {
    try {
      return squares[iRow][iColumn];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new InvalidSquareException();
    }
  }

  /**
   * Turns square at given coordinates into trap
   *
   * @param iRow    row index of square to modify
   * @param iColumn row index of square to modify
   */
  public void setTrap(int iRow, int iColumn) {
    Square target = getSquare(iRow, iColumn);
    if (target.isDefault() || target.isTrap()) {
      target.setType(SquareType.TRAP);
    } else {
      throw new IllegalArgumentException("Selected square is already Start or Finish");
    }
  }

  public int countTraps() {
    int sum = 0;
    for (Square[] row : squares) {
      for (Square s : row) {
        if (s.isTrap()) {
          ++sum;
        }
      }
    }
    return sum;
  }

  /**
   * Turns square at given coordinates into start
   *
   * @param iRow    row index of square to modify
   * @param iColumn row index of square to modify
   */
  public void setStart(int iRow, int iColumn) {
    Square newStart = getSquare(iRow, iColumn);
    newStart.setType(SquareType.START);
    if (currentStart != null) {
      currentStart.setType(SquareType.DEFAULT);
    }
    currentStart = newStart;
  }

  /**
   * Turns square at given coordinates into finish
   *
   * @param iRow    row index of square to modify
   * @param iColumn row index of square to modify
   */
  public void setFinish(int iRow, int iColumn) {
    Square newFinish = getSquare(iRow, iColumn);
    newFinish.setType(SquareType.FINISH);
    if (currentFinish != null) {
      currentFinish.setType(SquareType.DEFAULT);
    }
    currentFinish = newFinish;
  }


  /**
   * Creates a wall on the given side of the square with given coordinates
   *
   * @param iRow    row index of square to modify
   * @param iColumn row index of square to modify
   * @param pos     side of the square that shall get a wall
   */
  public void setWall(int iRow, int iColumn, WallPosition pos) {
    getSquare(iRow, iColumn).setWall(pos);
  }

  /**
   * Adds all available walls to game board
   */
  public void addAllWalls() {
    for (Square[] row : squares) {
      for (Square s : row) {
        s.setAllWall();
      }
    }
  }

  /**
   * Adds arbitrary walls to game board. Game is not ensured to be solvable.
   */
  public void addRandomWalls() {
    Random rand = new Random();
    int nMaxWalls = (nRows + 1) * (nColumns + 1);
    int nWalls = rand.nextInt(nMaxWalls);
    for (int iWall = 0; iWall < nWalls; ++iWall) {
      WallPosition pos = WallPosition.getRandom();
      int iRow = rand.nextInt(nRows);
      int iCol = rand.nextInt(nColumns);
      squares[iRow][iCol].setWall(pos);
    }
  }

  /**
   * Prints game board to stdout
   */
  public void print() {
    // top walls of first row
    System.out.print(squares[0][0].getTopLeftCorner().character);
    squares[0][0].printTopWall();
    for (int iCol = 1; iCol < nColumns; ++iCol) {
      BoxCharacter wallToPrint = BoxCharacter.merge(EnumSet.of(
              squares[0][iCol - 1].getTopRightCorner(),
              squares[0][iCol].getTopLeftCorner()));
      System.out.print(wallToPrint.character);
      squares[0][iCol].printTopWall();
    }
    System.out.print(squares[0][nColumns - 1].getTopRightCorner().character);
    System.out.println();

    // vertical walls of first row
    squares[0][0].printLeftWall();
    for (int iCol = 0; iCol < nColumns; ++iCol) {
      squares[0][iCol].print();
      squares[0][iCol].printRightWall();
    }
    System.out.println();

    for (int iRow = 1; iRow < nRows; ++iRow) {
      // leftmost column with only two corners to merge
      BoxCharacter wallToPrint = BoxCharacter.merge(EnumSet.of(
              squares[iRow - 1][0].getBottomLeftCorner(),
              squares[iRow][0].getTopLeftCorner()));
      System.out.print(wallToPrint.character);
      squares[iRow][0].printTopWall();

      // all inner squares
      for (int iCol = 1; iCol < nColumns; ++iCol) {
        wallToPrint = BoxCharacter.merge(EnumSet.of(
                squares[iRow - 1][iCol - 1].getBottomRightCorner(),
                squares[iRow - 1][iCol].getBottomLeftCorner(),
                squares[iRow][iCol - 1].getTopRightCorner(),
                squares[iRow][iCol].getTopLeftCorner()));
        System.out.print(wallToPrint.character);
        squares[iRow][iCol].printTopWall();
      }
      wallToPrint = BoxCharacter.merge(EnumSet.of(
              squares[iRow - 1][nColumns - 1].getBottomRightCorner(),
              squares[iRow][nColumns - 1].getTopRightCorner()));
      System.out.print(wallToPrint.character);
      System.out.println();

      // vertical walls
      squares[iRow][0].printLeftWall();
      for (int iCol = 0; iCol < nColumns; ++iCol) {
        squares[iRow][iCol].print();
        squares[iRow][iCol].printRightWall();
      }
      System.out.println();
    }

    // bottom walls of last row
    System.out.print(squares[nRows - 1][0].getBottomLeftCorner().character);
    squares[nRows - 1][0].printBottomWall();
    for (int iCol = 1; iCol < nColumns; ++iCol) {
      BoxCharacter wallToPrint = BoxCharacter.merge(EnumSet.of(
              squares[nRows - 1][iCol - 1].getBottomRightCorner(),
              squares[nRows - 1][iCol].getBottomLeftCorner()));
      System.out.print(wallToPrint.character);
      squares[nRows - 1][iCol].printBottomWall();
    }
    System.out.print(squares[nRows - 1][nColumns - 1].getBottomRightCorner().character);
    System.out.println();
  }

  public static void main(String[] args) {
    Labyrinth l = new Labyrinth(5, 5);
//    l.addAllWalls();
    l.addRandomWalls();
    l.setStart(0, 1);
    l.setFinish(4, 3);
    l.setTrap(0, 0);
    l.setTrap(1, 4);
    l.print();
  }
}
