package ooga.model;

import java.util.List;
import ooga.controller.Observable;

public interface GameModel extends Observable {

  /**
   * Takes the next turn.
   *
   * @param currentPlayer the player whose turn it is.
   */
  void doTurn(Player currentPlayer);

  /**
   * Performs a fund exchange between two players.
   *
   * @param receivingPlayer The player that is receiving funds
   * @param givingPlayer the player that is sending funds
   * @param amountToPay the amount that should be paid.
   */
  void payPlayer(Player receivingPlayer, Player givingPlayer, int amountToPay);

  /**
   * Performs an ownable exchange between two players.
   *
   * @param receivingPlayer The player that is receiving the ownables
   * @param givingPlayer The player that is sending the ownables.
   * @param exchangedOwnables The ownables that are exchanged.
   */
  void exchangeOwnables(Player receivingPlayer, Player givingPlayer,
      List<Ownable> exchangedOwnables);

  /**
   * Calls the event for the tile that the player is standing on.
   *
   * @param currentPlayer the player whose tile event should be called.
   */
  void callPlayerTileEvent(Player currentPlayer);

  /**
   * Finds the player object associated with a given name.
   *
   * @param name The name to search for
   * @return the player object associated with that name.
   */
  Player getPlayer(String name);

  /**
   * Returns the list of all players in the game.
   *
   * @return
   */
  List<Player> getPlayers();

  /**
   * Finds which tile is at a particular location on the board.
   *
   * @param location the position of the desired tile.
   * @return the Tile at that position
   */
  Tile getTile(int location);

  /**
   * Gets all of the tiles in the game.
   *
   * @return the list of all tiles.
   */
  List<Tile> getTiles();

  /**
   * Gets the bank object used in this game.
   *
   * @return the current bank.
   */
  Player getBank();

  /**
   * Gets the Randomizable used in this game.
   *
   * @return the randomizable that's used.
   */
  Randomizable getRandomizable();

  /**
   * Updates the player status with a new status effect.
   *
   * @param player the player whose status should be updated
   * @param status the new status effect to apply.
   */
  void updatePlayerStatus(Player player, StatusEffect status);

  /**
   * Instantly moves a player to a particular tile.
   *
   * @param player the player that should be moved.
   * @param newLocation the tile to move them to.
   */
  void jumpPlayerToTile(Player player, Tile newLocation);

  /**
   * Finds a tile associated with a particular name.
   *
   * @param name the name to search for
   * @return the tile with that name.
   */
  Tile getTileFromName(String name);

  /**
   * Gets the list of all cards used in the game.
   * Includes chance and community chest.
   *
   * @return the list of cards
   */
  List<Card> getCards();

  /**
   * Updates the tiles used in the game with a new set of tiles.
   *
   * @param newTiles the new set of tiles to use
   */
  void setTiles(List<Tile> newTiles);

  /**
   * Determines if a player has the full monopoly for a particular property.
   *
   * @param owner the player that might have a monopoly
   * @param ownedTile the tile that is part of the monopoly
   * @return whether or not the player has a monopoly
   */
  boolean checkMonopoly(Player owner, OwnableTile ownedTile);

  /**
   * Removes a player from the game.
   * Does not handle Ownable exchanges, debt, etc.
   *
   * @param toRemove the player that should be removed.
   */
  void removePlayer(Player toRemove);

  /**
   * Ends the game.  Can not be undone.
   * Does not immediately stop gameplay.
   */
  void declareGameOver();

  /**
   * Determines whether the game is over.
   *
   * @return whether the game is over or not
   */
  boolean checkIfGameOver();

  /**
   * Holds an auction for a particular tile.
   *
   * @param owner the owner of the tile
   * @param moneyReceiver who should receive the funds from the auction
   *                      This is usually the owner, but can be others in the case of bankruptcy
   * @param callerToExclude The player that should be excluded from the auction.  This is typically
   *                        the owner or the player that refused to purchase it.
   * @param auctionedOwnable the Ownable that is being auctions.
   */
  void holdAuction(Player owner, Player moneyReceiver, Player callerToExclude, Ownable auctionedOwnable);
}

