package ooga.controller;

/**
 * An observer interface to be implemented by classes that want to update their values based on
 * observables
 *
 * @author Hosam Tageldin
 */
public interface Observer {

  /**
   * Calls the necessary changes in the class that implements the Observer once the Observable
   * changes
   */
  void update();

}
