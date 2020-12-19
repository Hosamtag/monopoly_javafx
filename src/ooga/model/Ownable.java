package ooga.model;

public interface Ownable {

  /**
   * Finds the owner of this object.
   *
   * @return returns the owner.
   */
  Player getOwner();

  /**
   * Changes the owner of this object to someone else.
   *
   * @param newOwner the new owner of this object.
   */
  void changeOwner(Player newOwner);

  /**
   * Finds the name of this object.
   *
   * @return the name of this object.
   */
  String getName();

}
