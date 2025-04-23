package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;

import org.codeberry.berrytalk.chat.domain.ChatUser;

public record ChatUserInfo(
    String id,
    boolean isCreator,
    String lastMessageId,
    Date joinedAt) {

  public static ChatUserInfo from(ChatUser user) {
    if (user == null) {
      return null;
    }
    return new ChatUserInfo(
        user.getUserId(),
        user.getIsCreator(),
        user.getLastMessageId(),
        user.getJoinedAt());
  }

}
