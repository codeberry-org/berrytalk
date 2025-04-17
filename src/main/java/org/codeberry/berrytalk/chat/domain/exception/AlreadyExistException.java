package org.codeberry.berrytalk.chat.domain.exception;

public class AlreadyExistException extends RuntimeException {
  
  public AlreadyExistException(String message) {
    super(message);
  }
}
