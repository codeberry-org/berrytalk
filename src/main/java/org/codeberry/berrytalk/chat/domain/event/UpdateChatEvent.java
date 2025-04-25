package org.codeberry.berrytalk.chat.domain.event;

import java.util.Collections;
import java.util.List;

import org.codeberry.berrytalk.chat.domain.ChatUser;

public class UpdateChatEvent extends ChatEvent {
  private final String title;
  private final String imageId;
  private final List<ChatUser> users;

  public UpdateChatEvent(String chatId, String title, String imageId, List<ChatUser> users) {
    super(ChatEventType.UPDATE_CHAT, chatId);
    this.title = title;
    this.imageId = imageId;
    this.users = users;
  }

  public static UpdateChatEvent fromTitle(String chatId, String title) {
    return new UpdateChatEvent(chatId, title, null, null);
  }

  public static UpdateChatEvent fromImageId(String chatId, String imageId) {
    return new UpdateChatEvent(chatId, null, imageId, null);
  }

  public static UpdateChatEvent fromUsers(String chatId, List<ChatUser> users) {
    return new UpdateChatEvent(chatId, null, null, users);
  }
  
  public String getTitle() {
    return title;
  }

  public String getImageId() {
    return imageId;
  }

  public List<ChatUser> getUsers() {
    return Collections.unmodifiableList(users);
  }

  @Override
  public String toString() {
    return String.format("UpdateChatEvent(chatId=%s, title=%s, imageId=%s, users=%s)",
        getChatId(), title, imageId, users);
  }
}
