package ooga.model.monopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import ooga.controller.Observer;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.OwnableNotFoundException;
import ooga.exceptions.PlayerNotFoundException;
import ooga.model.Card;
import ooga.model.GameModel;
import ooga.model.Ownable;
import ooga.model.OwnableTile;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.StatusEffect;
import ooga.model.Tile;
import ooga.view.GameAlert;

public class MonopolyModel implements GameModel {

  public static final String BANK_NAME = "MonopolyBank";
  private static final String AUCTION_PROMPT = ResourceUtil.getResourceValue("WinningPrice");
  private static final String AUCTION_FOR = ResourceUtil.getResourceValue("AuctionFor");
  private static final String AUCTION_WINDOW_HEADER = ResourceUtil
      .getResourceValue("AuctionWindow");

  private final List<Player> myPlayers;
  private final MonopolyBoard myBoard;
  private final Randomizable myDie;
  private final Player myBank;
  private final GameAlert myGameAlert;
  private final List<Card> myCards;
  private final List<Observer> observers;
  private Boolean isActive = true;

  /**
   * @param tileInput The list of tiles for the game as constructed by a tile factory.
   * @param playerNames The list of players of the game as constructed by a player factor.
   * @param inputDie The randomizable used for the game as constructed by a randomizable factory.
   * @param cards The cards used in the game as constructed by a card factory.
   * @param gameAlert The gameAlert object to be used for notifications and user input.
   */
  public MonopolyModel(List<Tile> tileInput, List<Player> playerNames, Randomizable inputDie,
      List<Card> cards, GameAlert gameAlert) {
    observers = new ArrayList<>();
    myDie = inputDie;
    myBoard = new MonopolyBoard(tileInput);
    myPlayers = playerNames;
    myBank = createBank();
    myCards = cards;
    myGameAlert = gameAlert;
  }

  /**
   * Sets the tiles of the game to a new set of tiles.
   *
   * @param newTiles the new set of tiles to use
   */
  public void setTiles(List<Tile> newTiles) {
    myBoard.setTiles(newTiles);
  }

  /**
   * @return The list of cards being used in the game.
   */
  public List<Card> getCards() {
    return myCards;
  }

  private Player createBank() {
    MonopolyPlayer bank = new MonopolyBank(0);
    for (Tile eachTile : myBoard.getTiles()) {
      if (eachTile instanceof OwnableTile) {
        bank.addOwnable((OwnableTile) eachTile);
        ((OwnableTile) eachTile).changeOwner(bank);
      }
    }
    return bank;
  }

  /**
   * Swaps the ownership of a set of ownables between two Player entities.
   *
   * @param receivingPlayer   the player that will be receiving the ownables
   * @param givingPlayer      the player that will be giving the ownables
   * @param exchangedOwnables the list of ownables that will be exchanged between the players
   */
  public void exchangeOwnables(Player receivingPlayer, Player givingPlayer,
      List<Ownable> exchangedOwnables) {
    for (Ownable eachOwnable : exchangedOwnables) {
      givingPlayer.removeOwnable(eachOwnable);
      receivingPlayer.addOwnable(eachOwnable);
      eachOwnable.changeOwner(receivingPlayer);
    }
  }

  /**
   * @return The randomizable being used in the game.
   */
  public Randomizable getRandomizable() {
    return myDie;
  }

  /**
   * Updates the status of a player to she specified status.
   *
   * @param player the player whose status should be updated
   * @param status the new status effect to apply.
   */
  public void updatePlayerStatus(Player player, StatusEffect status) {
    player.updateStatusEffect(status);
  }

  /**
   * Jumps a player to a new tile in the board, setting that as their location.
   *
   * @param player      the player that should be moved.
   * @param newLocation the tile to move them to.
   */
  public void jumpPlayerToTile(Player player, Tile newLocation) {
    player.updateCurrentTile(newLocation);
  }

  /**
   * Exchanges funds between players. This method is blind to bankruptcy.
   *
   * @param receivingPlayer the player to receive the funds
   * @param givingPlayer    the player that is giving the funds
   * @param amountToPay     the amount of funds to exchange
   */
  public void payPlayer(Player receivingPlayer, Player givingPlayer,
      int amountToPay) {
    givingPlayer.updateFunds(givingPlayer.getFunds() - amountToPay);
    receivingPlayer.updateFunds(receivingPlayer.getFunds() + amountToPay);
  }

  /**
   * Processes the core steps of a Player's turn. 1) Checks the values on the dice 2) moves the
   * player to the new tile.
   *
   * @param currentPlayer the player that is taking this turn
   */
  public void doTurn(Player currentPlayer) {
    currentPlayer.doTurn(myDie, myBoard, this, myGameAlert);
  }

  /**
   * Triggers the event for the tile that the player is currently on.
   *
   * @param currentPlayer the player whose turn it currently is
   */
  public void callPlayerTileEvent(Player currentPlayer) {
    currentPlayer.getCurrentTile().callMyEvent(currentPlayer, this);
    notifyObservers();
  }

  /**
   * Notifies the observers of the model that something has changed.
   */
  @Override
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  /**
   * Adds an obersver to the list of observers listening to the model.
   *
   * @param o A new observer object that is to be observing the model.
   */
  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  /**
   * Returns the player with a particular name. ASSUMPTIONS: No players have the same name If
   * players do have the same name, only the first will be returned
   *
   * @param name the name of the player to find
   * @return the Player object associated with the given name
   */
  public Player getPlayer(String name) {
    for (Player eachPlayer : myPlayers) {
      if (eachPlayer.getName().equals(name)) {
        return eachPlayer;
      }
    }
    throw new PlayerNotFoundException(ResourceUtil.getResourceValue("NoPlayerName") + name);
  }

  /**
   * @return An unmodifiableList of the tiles being used in the game.
   */
  public List<Tile> getTiles() {
    return Collections.unmodifiableList(myBoard.getTiles());
  }

  /**
   * @return An unmodifiableList of the players of the game.
   */
  public List<Player> getPlayers() {
    return Collections.unmodifiableList(myPlayers);
  }

  /**
   * Return the tile at the specified index.
   *
   * @param location the position of the desired tile.
   * @return The tile at the specified index.
   */
  public Tile getTile(int location) {
    return myBoard.getTiles().get(location);
  }

  /**
   * Return the tile in the game with the specified name.
   *
   * @param name the name to search for
   * @return The tile with that name.
   */
  public Tile getTileFromName(String name) {
    for (Tile eachTile : myBoard.getTiles()) {
      if (eachTile.getName().equalsIgnoreCase(name)) {
        return eachTile;
      }
    }
    throw new OwnableNotFoundException(
        String.format(ResourceUtil.getResourceValue("NoTileFound"), name));
  }

  /**
   * @return The randomizable being used in the game.
   */
  public Randomizable getDie() {
    return myDie;
  }

  /**
   * @return The bank being used in the game.
   */
  public Player getBank() {
    return myBank;
  }

  /**
   * Checks to see if a player owns all tiles within a given tile's monopoly.
   *
   * @param owner     The player to check monopoly for
   * @param ownedTile the tile that is a member of the desired monopoly
   * @return whether or not the player has a full monopoly
   */
  public boolean checkMonopoly(Player owner, OwnableTile ownedTile) {
    boolean ownsAllTiles = true;
    String monopoly = ownedTile.getMonopoly();
    Set<OwnableTile> tilesInMonopoly = myBoard.getTilesInMonopoly(monopoly);
    for (OwnableTile eachTile : tilesInMonopoly) {
      if (!eachTile.getOwner().equals(owner)) {
        ownsAllTiles = false;
      }
    }
    return ownsAllTiles;
  }

  /**
   * Removes a player from the game.
   *
   * @param toRemove the player that should be removed.
   */
  public void removePlayer(Player toRemove) {
    myPlayers.remove(toRemove);
  }

  /**
   * Declares that the game has been finished.
   */
  public void declareGameOver() {
    isActive = false;
  }

  /**
   * @return Boolean that indicates if the game is over.
   */
  public boolean checkIfGameOver() {
    return !isActive;
  }

  /**
   * Holds an auction for the specified ownable.
   *
   * @param owner            the owner of the tile
   * @param moneyReceiver    who should receive the funds from the auction This is usually the
   *                         owner, but can be others in the case of bankruptcy
   * @param callerToExclude  The player that should be excluded from the auction.  This is typically
   *                         the owner or the player that refused to purchase it.
   * @param auctionedOwnable the Ownable that is being auctions.
   */
  public void holdAuction(Player owner, Player moneyReceiver, Player callerToExclude,
      Ownable auctionedOwnable) {
    List<String> auctionablePlayerNames = getPlayerNamesWithoutOne(callerToExclude);
    List<String> results = myGameAlert
        .createAuctionDialog(auctionablePlayerNames, AUCTION_PROMPT, AUCTION_WINDOW_HEADER,
            AUCTION_FOR + auctionedOwnable.getName());
    try {
      payPlayer(moneyReceiver, getPlayer(results.get(0)), Integer.parseInt(results.get(1)));
      exchangeOwnables(getPlayer(results.get(0)), owner, Arrays.asList(auctionedOwnable));
    } catch (Exception e) {
      exchangeOwnables(myBank, owner, Arrays.asList(auctionedOwnable));
    }
  }

  private List<String> getPlayerNamesWithoutOne(Player excluded) {
    List<Player> allPlayers = List.copyOf(getPlayers());
    List<String> allPlayerNames = new ArrayList<>();
    for (Player player : allPlayers) {
      allPlayerNames.add(player.getName());
    }
    allPlayerNames.remove(excluded.getName());
    return allPlayerNames;
  }

}
