package org.codeberry.berrytalk.chat.app.dto;

import org.codeberry.berrytalk.chat.domain.Chat;

public record ChatInfo() {
  
  public static ChatInfo from(Chat chat) {
    // TODO
    return new ChatInfo();
  }
}
