package edu.duke.ece651.mp.common;

public abstract class MoveChecking<T> {
  private final MoveChecking<T> next;
  String moveStatus;

  public MoveChecking(MoveChecking<T> next) {
    this.next = next;
  }

  protected abstract String checkMyRule(Map<T> map, String source, String destination, int movingunits);

  public String checkMoving(Map<T> map, String source, String destination, int movingunits) {
    // update moveStatus
    moveStatus = "player color placeholder: Move order from " + source + " into " + destination + " with " + movingunits
        + " units was ";

    if (checkMyRule(map, source, destination, movingunits) != null) {
      return checkMyRule(map, source, destination, movingunits);
    }
    if (next != null) {
      return next.checkMoving(map, source, destination, movingunits);
    }
    moveStatus += "successful!";
    return null;
  }
}
