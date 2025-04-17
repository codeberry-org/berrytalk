package org.codeberry.berrytalk.chat.app.dto;

import org.codeberry.berrytalk.chat.domain.Message;

public record MessageInfo() {
  
  public static MessageInfo from(Message message) {
    // TODO
    return new MessageInfo();
  }
}
