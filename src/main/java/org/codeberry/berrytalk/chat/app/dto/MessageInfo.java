package org.codeberry.berrytalk.chat.app.dto;

import org.codeberry.berrytalk.chat.common.util.Args;
import org.codeberry.berrytalk.chat.domain.Message;

public record MessageInfo() {
  
  public static MessageInfo from(Message message) {
    Args.requireNotNull(message, "message");
    // TODO
    return new MessageInfo();
  }
}
