package ooga.controller.readers.gameobjectsreaders;


import ooga.controller.GameObjectsReader;

/**
 * reads the information from the data files about a single tile
 */
public class TileReader extends GameObjectsReader {

  private static final String TILE_PATH =  "data/gameversions/tiles/";
  /**
   * this gets the path to the get tiles for this specific monopoly version
   */
  @Override
  protected void setPath() {
    filePath = TILE_PATH;
  }


}