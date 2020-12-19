package ooga.model;

public interface Mortgageable {

  /**
   * Changes the current mortgage status of this object.
   * Swaps between mortgaged and unmortgaged.
   */
  void changeMortgageStatus();

  /**
   * Calculates the amount it would cost to remove a mortgage.
   *
   * @return the cost to unmortgage
   */
  int amountToRemoveMortgage();

  /**
   * Determines whether this object is mortgaged or not
   *
   * @return whether this object is mortgaged
   */
  boolean checkIfMortgaged();

}
