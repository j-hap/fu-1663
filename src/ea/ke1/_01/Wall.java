package ea.ke1._01;

/**
 * Mutable boolean wrapper class to share
 * walls between game board squares
 */
class Wall {
  private boolean active = false;

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
