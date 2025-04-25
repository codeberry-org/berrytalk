package org.codeberry.berrytalk.chat.domain.event;

public class SeenMessageEvent extends ChatEvent {
  private final String userId;
  private final String lastMessageId;

  public SeenMessageEvent(String chatId, String userId, String lastMessageId) {
    super(ChatEventType.SEEN_MESSAGE, chatId);
    this.userId = userId;
    this.lastMessageId = lastMessageId;
  }

  public String getUserId() {
    return userId;
  }

  public String getLastMessageId() {
    return lastMessageId;
  }

  @Override
  public String toString() {
    return String.format("SeenMessageEvent(chatId=%s, userId=%s, lastMessageId=%s)",
        getChatId(), userId, lastMessageId);
  }
}
