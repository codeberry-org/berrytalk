package org.codeberry.berrytalk.chat.app.dto;

import org.codeberry.berrytalk.chat.domain.Message;

public record MessageInfo() {
  
  public static MessageInfo from(Message message) {
    if (message == null) {
      return null;
    }
    // TODO
    return new MessageInfo();
  }
}
