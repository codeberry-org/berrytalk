package org.codeberry.berrytalk.chat.domain.event;

public abstract class ChatEvent {
  private final String type;
  private final String chatId;

  protected ChatEvent(String type, String chatId) {
    this.chatId = chatId;
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getChatId() {
    return chatId;
  }
}
