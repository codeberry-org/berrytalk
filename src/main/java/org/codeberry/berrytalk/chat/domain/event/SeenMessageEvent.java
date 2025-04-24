package org.codeberry.berrytalk.chat.domain.event;

public class SeenMessageEvent extends ChatEvent {
  public static final String TYPE = "SEEN_MESSAGE";

  private final String userId;
  private final String lastMessageId;

  public SeenMessageEvent(String chatId, String userId, String lastMessageId) {
    super(chatId, TYPE);
    this.userId = userId;
    this.lastMessageId = lastMessageId;
  }

  public String getUserId() {
    return userId;
  }

  public String getLastMessageId() {
    return lastMessageId;
  }
}
