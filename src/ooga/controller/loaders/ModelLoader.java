package ooga.controller.loaders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;
import ooga.controller.DataVersionReader;
import ooga.controller.factory.FactoryCreator;
import ooga.controller.util.ResourceUtil;
import ooga.exceptions.InvalidObjectErrorException;
import ooga.model.Card;
import ooga.model.GameModel;
import ooga.model.Player;
import ooga.model.Randomizable;
import ooga.model.Tile;
import ooga.model.monopoly.MonopolyModel;
import ooga.view.GameAlert;
import ooga.view.alerts.MonopolyGameAlert;

/**
 * Loads the model using the FactoryCreator and the user input or the data loaded in from the files
 *
 * @author Hosam Tageldin
 */
public class ModelLoader {

  private final FactoryCreator myFactoryCreator;
  private final DataVersionReader myNewReader;
  private final List<Map<String, String>> myPlayerMaps;
  private final String[] myRandomizableDetails;
  private List<String> playerIcons;

  public ModelLoader(DataVersionReader newReader, List<Map<String, String>> playerMaps,
      String[] randomizableDetails) {
    myFactoryCreator = new FactoryCreator();
    this.myNewReader = newReader;
    this.myPlayerMaps = playerMaps;
    this.myRandomizableDetails = randomizableDetails;
  }

  /**
   * Uses the FactoryCreator to instantiate all the parameters of the MonopolyModel and then creates
   * an instance of the GameModel to be used by the controller
   *
   * @return the GameModel created using the factories
   */
  public GameModel getGameModel() {
    try {
      List<Tile> tileList = myFactoryCreator.createTiles(myNewReader.getTileMaps());
      List<Player> playerList = myFactoryCreator.createPlayers(myPlayerMaps, tileList);
      playerIcons = myFactoryCreator.getPlayerIcons();
      Randomizable myDie = myFactoryCreator.createRandomizables(myRandomizableDetails);
      List<Card> cardList = myFactoryCreator.createCards(myNewReader.getCardMaps());
      GameAlert gameAlert = new MonopolyGameAlert();
      return new MonopolyModel(tileList, playerList, myDie, cardList, gameAlert);
    } catch (IOException | InvalidKeyException e) {
      throw new InvalidObjectErrorException(ResourceUtil.getResourceValue("PropertyException"));
    }
  }

  /**
   * @return the list of player icons for the game
   */
  public List<String> getPlayerIcons() {
    return playerIcons;
  }

}
