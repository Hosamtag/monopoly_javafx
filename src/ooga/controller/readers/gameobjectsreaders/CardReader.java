package ooga.controller.readers.gameobjectsreaders;

import ooga.controller.GameObjectsReader;

/**
 * Reads the information of an an individual chance card
 */
public class CardReader extends GameObjectsReader {

  private static final String CARD_PATH = "data/gameversions/decks/cards/";

  /**
   * this gets the path to the get cards for this specific monopoly version
   */
  @Override
  protected void setPath() {
    filePath = CARD_PATH;
  }


}
