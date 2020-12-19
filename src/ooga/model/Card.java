package ooga.model;

public interface Card {

  /**
   * Initiates the action specified by this card.
   *
   * @param player The player that drew the card
   * @param model the model used for this game.
   */
  void callCardAction(Player player, GameModel model);

  /**
   * Gets the description for this card
   *
   * @return the description
   */
  String getCardDescription();

}
