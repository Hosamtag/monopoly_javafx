package ooga.controller.order;

import java.util.List;
import java.util.Random;
import ooga.controller.Order;
import ooga.model.Player;

/**
 * Normal order will return the next player in the list for the next players turn
 *
 * @author Alex Jimenez, Hosam Tageldin
 */
public class RandomOrder implements Order {

  /**
   * Returns a random player in the list to be the next player turn
   *
   * @param currentPlayer Player who just undertook his turn
   * @param allPlayers    all the players in the game
   * @return a random player in the list
   */
  @Override
  public Player getNextPlayer(Player currentPlayer, List<Player> allPlayers) {
    Random rand = new Random();
    int randomInt = rand.nextInt(allPlayers.size());
    return allPlayers.get(randomInt);
  }
}
