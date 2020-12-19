package ooga.controller.order;

import java.util.List;
import ooga.controller.Order;
import ooga.model.Player;

/**
 * Normal order will return the next player in the list for the next players turn
 *
 * @author Alex Jimenez, Hosam Tageldin
 */
public class NormalOrder implements Order {

  /**
   * Returns the next player in the list
   *
   * @param currentPlayer Player who just undertook his turn
   * @param allPlayers    all the players in the game
   * @return the next Player in the list
   */
  @Override
  public Player getNextPlayer(Player currentPlayer, List<Player> allPlayers) {
    return allPlayers.get((allPlayers.indexOf(currentPlayer) + 1) % allPlayers.size());
  }
}
