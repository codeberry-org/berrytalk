package org.codeberry.berrytalk.chat.domain.event;

import org.codeberry.berrytalk.chat.domain.Message;

public class NewMessageEvent extends ChatEvent {
  private final Message message;

  public NewMessageEvent(String chatId, Message message) {
    super(ChatEventType.NEW_MESSAGE, chatId);
    this.message = message;
  }

  public Message getMessage() {
    return message;
  }
  
  @Override
  public String toString() {
    return String.format("NewMessageEvent(chatId=%s, message=%s)",
        getChatId(), message);
  }
}
