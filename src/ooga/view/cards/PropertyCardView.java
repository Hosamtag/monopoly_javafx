package ooga.view.cards;

import javafx.scene.layout.HBox;
import ooga.controller.util.ResourceUtil;
import ooga.view.CardView;

/**
 * Creates a game card for the Property Tile Views holding the costs of upgrades and the rent
 * structures for each tile
 *
 * @author Hosam Tageldin
 */
public class PropertyCardView extends CardView {

  private static final String BACKGROUND_COLOR = "-fx-background-color: ";

  /**
   * Constructor
   *
   * @param name
   * @param color
   * @param rentStructure
   * @param upgradeCosts
   */
  public PropertyCardView(String name, String color, String[] rentStructure,
      String[] upgradeCosts) {
    super(name);
    HBox propertyColor = new HBox();
    propertyColor.setId("CardColor");
    propertyColor.setStyle(BACKGROUND_COLOR + color);
    myCard.setTop(propertyColor);
    createPropertyDescription(rentStructure, upgradeCosts);
  }

  private void createPropertyDescription(String[] rentStructure, String[] upgradeCosts) {
    createHBox(ResourceUtil.getResourceValue("Rent") + rentStructure[0]);
    if (!upgradeCosts[0].equals("")) {
      int i;
      for (i = 1; i < rentStructure.length - 1; i++) {
        createHBox(String.format(ResourceUtil.getResourceValue("WithHouse"), i, rentStructure[i]));
      }
      createHBox(ResourceUtil.getResourceValue("WithHotel") + rentStructure[i]);
      createHBox(ResourceUtil.getResourceValue("HouseCost") + upgradeCosts[0]);
      createHBox(String.format(ResourceUtil.getResourceValue("HotelCost"), upgradeCosts[0]));
    }
  }

}
