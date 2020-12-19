package ooga.controller;

import java.util.List;
import ooga.model.Player;

/**
 * Interface for determining the order of the players during a game.
 *
 * @author Hosam Tageldin
 */
public interface Order {

  /**
   * Determines the player whose turn is up next
   *
   * @param currentPlayer Player who just undertook his turn
   * @param allPlayers    all the players in the game
   * @return the player whose turn is current is
   */
  Player getNextPlayer(Player currentPlayer, List<Player> allPlayers);

}
