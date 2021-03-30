package ea.ke1._01;

/**
 * Exception for addressing of non-existent squares in Labyrinth
 */
class InvalidSquareException extends ArrayIndexOutOfBoundsException {
  public InvalidSquareException() {
    super("Given Square does not exist in Labyrinth.");
  }
}
