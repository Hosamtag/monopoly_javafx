package ooga.model;

import ooga.controller.util.ResourceUtil;
import ooga.view.GameAlert;

public abstract class TileEvent {

  protected static final String ACTION_CARD =  ResourceUtil.getResourceValue("ActionCard");
  protected static final String TILE_EVENT = ResourceUtil.getResourceValue("TileEvent");
  protected final GameAlert gameAlert;

  /**
   * Creates a new TileEvent object.
   *
   * @param alert the alert object used to display notifications.
   */
  public TileEvent(GameAlert alert) {
    gameAlert = alert;
  }

}
