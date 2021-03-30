package ea.ke1._01;

/**
 * Mutable boolean wrapper class to share
 * walls between game board squares
 */
class Wall {
  private boolean active = false;

  Wall(boolean active) {
    this.active = active;
  }

  void activate() {
    active = true;
  }

  void deactivate() {
    active = false;
  }

  boolean isActive() {
    return active;
  }
}
