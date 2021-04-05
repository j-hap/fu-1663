package ea.ke1._01;

enum SquareType {
  DEFAULT("   "), START(" S "), FINISH(" F "), TRAP(" T ");
  private final String string;

  SquareType(String string) {
    this.string = string;
  }

  public void print() {
    System.out.println(this.string);
  }
}
