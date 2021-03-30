package ea.ke1._01;

enum SquareType {
  DEFAULT("   "), START(" S "), FINISH(" F "), TRAP(" T ");

  public final String string;

  SquareType(String string) {
    this.string = string;
  }
}
