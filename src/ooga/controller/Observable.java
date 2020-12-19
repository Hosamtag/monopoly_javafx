package ooga.controller;

/**
 * Defines the methods for the object that is to be observed so that the Observer can update
 * properly based off Observable changes
 *
 * @author Hosam Tageldin
 */
public interface Observable {

  /**
   * Notifies the Observers that a change in the observable has been made
   */
  void notifyObservers();

  /**
   * Adds an Observer to a data structure holding all of the Observers that the Observable must
   * notify on a change
   *
   * @param o Observer to add to the Observable data structure
   */
  void addObserver(Observer o);
}
