package ooga.exceptions;

public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException(String errorMessage) {
    super(errorMessage);
  }
}
