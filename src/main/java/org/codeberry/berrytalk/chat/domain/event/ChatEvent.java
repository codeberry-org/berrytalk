package org.codeberry.berrytalk.chat.domain.event;

public abstract class ChatEvent {
  private final ChatEventType type;
  private final String chatId;

  protected ChatEvent(ChatEventType type, String chatId) {
    this.type = type;
    this.chatId = chatId;
  }

  public ChatEventType getType() {
    return type;
  }

  public String getChatId() {
    return chatId;
  }
}
