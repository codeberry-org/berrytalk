package org.codeberry.berrytalk.chat.domain.exception;

public class InvalidMessageException extends RuntimeException {

  public InvalidMessageException(String message) {
    super(message);
  }
}
