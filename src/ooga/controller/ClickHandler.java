package ooga.controller;

/**
 * Interface to control what happens once a tile gets clicked on in the GUI
 *
 * @author Hosam Tageldin
 */
public interface ClickHandler extends Observer {

  /**
   * The method that will be called once a tile gets clicked on
   */
  void handleClick();
}
