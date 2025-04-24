package org.codeberry.berrytalk.chat.domain.event;

import org.codeberry.berrytalk.chat.domain.Message;

public class NewMessageEvent extends ChatEvent {
  public static final String TYPE = "NEW_MESSAGE";

  private final Message message;

  public NewMessageEvent(String chatId, Message message) {
    super(TYPE, chatId);
    this.message = message;
  }

  public Message getMessage() {
    return message;
  }
}
