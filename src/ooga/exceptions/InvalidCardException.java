package ooga.exceptions;

public class InvalidCardException extends RuntimeException {

  public InvalidCardException(String errorMessage) {
    super(errorMessage);
  }

}
