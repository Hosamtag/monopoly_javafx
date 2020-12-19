package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidOperationException;
import ooga.exceptions.OwnableNotFoundException;
import ooga.model.GameModel;
import ooga.model.Mortgageable;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Upgradable;
import ooga.view.alerts.ErrorAlert;

/**
 * Handles the functionality of the buttons in the game
 *
 * @author Hosam Tageldin
 */
public class ButtonHandler {

  private final ErrorAlert errorAlert;

  public ButtonHandler() {
    errorAlert = new ErrorAlert();
  }

  private static final int TRADE_MONEY_PAY_FIRST_PLAYER_INDEX = 5;
  private static final int TRADE_MONEY_PAY_SECOND_PLAYER_INDEX = 2;
  private static final int TRADE_FIRST_PLAYER_NAME_INDEX = 0;
  private static final int TRADE_SECOND_PLAYER_NAME_INDEX = 3;
  private static final int TRADE_PROPERTY_FIRST_PLAYER_INDEX = 1;
  private static final int TRADE_PROPERTY_SECOND_PLAYER_INDEX = 4;

  /**
   * Calls the model to handle the mortgaging of a property in the game
   *
   * @param myMonopolyModel    the model for the game
   * @param propertyToMortgage string representing the property to mortgage
   */
  public void handleMortgage(GameModel myMonopolyModel, String propertyToMortgage) {
    try {
      if (!propertyToMortgage.equals("")) {
        propertyToMortgage = propertyToMortgage.trim();
        Mortgageable tile = (Mortgageable) getOwnable(myMonopolyModel, propertyToMortgage);
        tile.changeMortgageStatus();
      }
    } catch (InvalidOperationException | OwnableNotFoundException | ClassCastException e) {
      errorAlert.createErrorAlert(e.getMessage());
    }
  }

  private Ownable getOwnable(GameModel myMonopolyModel, String property) {
    List<Player> playerList = myMonopolyModel.getPlayers();
    for (Player player : playerList) {
      for (Ownable ownable : player.getOwnables()) {
        if (ownable.getName().equals(property)) {
          return ownable;
        }
      }
    }
    throw new OwnableNotFoundException(
        String.format(ResourceUtil.getResourceValue("NoPlayer"), property));
  }

  /**
   * Calls the model to handle the upgrading of a property in the game
   *
   * @param myMonopolyModel   the model for the game
   * @param propertyToUpgrade string representing the property to upgrade
   */
  public void handleUpgrade(GameModel myMonopolyModel, String propertyToUpgrade) {
    try {
      if (!propertyToUpgrade.equals("")) {
        propertyToUpgrade = propertyToUpgrade.trim();
        Upgradable tile = (Upgradable) getOwnable(myMonopolyModel, propertyToUpgrade);
        tile.upgrade(myMonopolyModel);
      }
    } catch (OwnableNotFoundException | InvalidOperationException | ClassCastException e) {
      errorAlert.createErrorAlert(e.getMessage());
    }
  }

  /**
   * Calls the model to handle the trading of Ownables and money in the game
   *
   * @param myMonopolyModel the model for the game
   * @param inputs          holding players involved, the money to trade and the properties
   */
  public void handleTrade(GameModel myMonopolyModel, List<String> inputs) {
    if (inputs.size() > 0) {
      tradeItems(myMonopolyModel, inputs);
      tradeMoney(myMonopolyModel, inputs);
    }
  }


  private void tradeItems(GameModel myMonopolyModel, List<String> responses) {
    try {
      myMonopolyModel.exchangeOwnables(
          getPlayer(myMonopolyModel, responses.get(TRADE_SECOND_PLAYER_NAME_INDEX)),
          getPlayer(myMonopolyModel, responses.get(TRADE_FIRST_PLAYER_NAME_INDEX)),
          tradedItems(myMonopolyModel, responses.get(TRADE_PROPERTY_FIRST_PLAYER_INDEX)));
      myMonopolyModel.exchangeOwnables(
          getPlayer(myMonopolyModel, responses.get(TRADE_FIRST_PLAYER_NAME_INDEX)),
          getPlayer(myMonopolyModel, responses.get(TRADE_SECOND_PLAYER_NAME_INDEX)),
          tradedItems(myMonopolyModel, responses.get(TRADE_PROPERTY_SECOND_PLAYER_INDEX)));
    } catch (InvalidOperationException e) {
      errorAlert.createErrorAlert(e.getMessage());
    }
  }

  private void tradeMoney(GameModel myMonopolyModel, List<String> responses) {
    int payFirstPlayer = Integer.parseInt(responses.get(TRADE_MONEY_PAY_FIRST_PLAYER_INDEX));
    int paySecondPlayer = Integer.parseInt(responses.get(TRADE_MONEY_PAY_SECOND_PLAYER_INDEX));
    myMonopolyModel
        .payPlayer(getPlayer(myMonopolyModel, responses.get(TRADE_FIRST_PLAYER_NAME_INDEX)),
            getPlayer(myMonopolyModel, responses.get(TRADE_SECOND_PLAYER_NAME_INDEX)),
            payFirstPlayer);
    myMonopolyModel
        .payPlayer(getPlayer(myMonopolyModel, responses.get(TRADE_SECOND_PLAYER_NAME_INDEX)),
            getPlayer(myMonopolyModel, responses.get(TRADE_FIRST_PLAYER_NAME_INDEX)),
            paySecondPlayer);
  }

  private List<Ownable> tradedItems(GameModel myMonopolyModel, String tradedOwnables) {
    List<Ownable> tradedOwnableList = new ArrayList<>();
    try {
      for (String ownableString : tradedOwnables.split(",")) {
        ownableString = ownableString.trim();
        if (!ownableString.equals("")) {
          Ownable ownable = getOwnable(myMonopolyModel, ownableString);
          tradedOwnableList.add(ownable);
        }
      }
    } catch (OwnableNotFoundException e) {
      errorAlert.createErrorAlert(e.getMessage());
    }
    return tradedOwnableList;
  }


  private Player getPlayer(GameModel myMonopolyModel, String playerName) {
    for (Player player : myMonopolyModel.getPlayers()) {
      if (player.getName().equals(playerName)) {
        return player;
      }
    }
    return null;
  }


}
