package ooga.model.monopoly;

public class MonopolyBank extends MonopolyPlayer {

  /**
   * @param startingBalance Value resulting from using the MonopolyPlayer interface. Is overridden to give
   *                        the bank "infinite" money.
   */
  protected MonopolyBank(int startingBalance) {
    super(MonopolyModel.BANK_NAME, null, Integer.MAX_VALUE);
  }

  /**
   * Does not change the banks funds. Result from using the MonopolyPlayer interface.
   *
   * @param newBalance the new balance of the Player's account
   */
  @Override
  public void updateFunds(int newBalance) {
    super.updateFunds(this.getFunds());
  }

}
