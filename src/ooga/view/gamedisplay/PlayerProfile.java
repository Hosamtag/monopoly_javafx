package ooga.view.gamedisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ooga.model.Mortgageable;
import ooga.model.Ownable;
import ooga.model.Player;
import ooga.model.Upgradable;
import ooga.model.Buildings;

/**
 * Creates the player summaries that are used to display player names, icons, funds, properties,
 * houses, and hotels
 *
 * @author Isabella Knox, Hosam Tageldin
 */
public class PlayerProfile {

  private static final int IMG_SIZE = 19;
  private static final String BUILDINGS_IMAGE_PATH = "/buildings/";
  private static final String PNG_EXTENSION = ".png";
  private final List<Player> myPlayers;
  private final Map<Player, ImageView> myIcons;
  private List<ComboBox<BorderPane>> playerList;
  private final int activePlayer;


  /**
   * Constructor
   *
   * @param players
   * @param icons
   * @param activePlayer
   */
  public PlayerProfile(List<Player> players, Map<Player, ImageView> icons, int activePlayer) {
    myPlayers = players;
    myIcons = icons;
    this.activePlayer = activePlayer;
  }

  /**
   * Creates and returns all the comboBoxes that holds all their current information
   *
   * @return playerList
   */
  public List<ComboBox<BorderPane>> createComboBox() {
    playerList = new ArrayList<>();
    for (int i = 0; i < myPlayers.size(); i++) {
      playerList.add(new ComboBox<>());
      playerList.get(i).setId("player" + i);
    }
    addPlayerNames(playerList);
    addPlayerInformation(playerList);
    playerList.get(activePlayer).setId("ActivePlayer");
    return playerList;
  }

  private void addPlayerNames(List<ComboBox<BorderPane>> playerList) {
    for (int i = 0; i < myPlayers.size(); i++) {
      BorderPane newBox = new BorderPane();
      Text newText = new Text(myPlayers.get(i).getName() + " $" + myPlayers.get(i).getFunds());
      newBox.setLeft(newText);
      newBox.setRight(myIcons.get(myPlayers.get(i)));
      playerList.get(i).setValue(newBox);
    }
  }


  private void addPlayerInformation(List<ComboBox<BorderPane>> playerList) {
    for (int i = 0; i < myPlayers.size(); i++) {
      List<BorderPane> propertyPane = createPropertyTile(i, myPlayers.get(i).getOwnables().size());
      for (BorderPane borderPane : propertyPane) {
        playerList.get(i).getItems().add(borderPane);
      }
    }
  }

  private List<BorderPane> createPropertyTile(int playerNumber, int propertiesTotal) {
    List<BorderPane> propertiesList = new ArrayList<>();
    for (int i = 0; i < propertiesTotal; i++) {
      Ownable tile = myPlayers.get(playerNumber).getOwnables().get(i);
      BorderPane propertyPane = new BorderPane();
      Text tempText = new Text(tile.getName());
      propertyPane.setLeft(tempText);
      try {
        if (((Mortgageable) tile).checkIfMortgaged()) {
          tempText.setFill(Color.RED);
          tempText.setStrikethrough(true);
        }
        propertyPane.setRight(createImageHBox(((Upgradable) tile).getCurrentBuilding()));
      } catch (ClassCastException e) {
        //do nothing
      }
      propertiesList.add(propertyPane);
    }
    return propertiesList;
  }

  private HBox createImageHBox(Buildings currentBuilding) {
    HBox imageBox = new HBox();
    if (currentBuilding.equals(Buildings.HOTEL)) {
      createImageView(imageBox, "hotel");
    } else {
      int numberOfUpgrades = getIndexOf(currentBuilding);
      for (int i = 0; i < numberOfUpgrades; i++) {
        createImageView(imageBox, "house");
      }
    }
    return imageBox;
  }

  private void createImageView(HBox box, String typeString) {
    ImageView buildingImage =
        new ImageView(BUILDINGS_IMAGE_PATH + typeString + PNG_EXTENSION);
    box.getChildren().add(buildingImage);
    buildingImage.setFitHeight(IMG_SIZE);
    buildingImage.setFitWidth(IMG_SIZE);
  }

  /**
   * Returns the comboBoxes for the players holding all their information
   *
   * @return playerList
   */
  public List<ComboBox<BorderPane>> getComboBox() {
    return playerList;
  }

  /**
   * Returns the index of the building
   *
   * @param building
   * @return
   */
  public int getIndexOf(Buildings building) {
    for (int i = 0; i < Buildings.values().length; i++) {
      if (Buildings.values()[i].equals(building)) {
        return i;
      }
    }
    return -1;
  }
}
