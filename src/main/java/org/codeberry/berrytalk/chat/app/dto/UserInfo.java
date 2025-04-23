package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;

import org.codeberry.berrytalk.chat.common.util.Args;
import org.codeberry.berrytalk.chat.domain.ChatUser;

public record UserInfo(
    String id,
    Boolean isCreator,
    String lastMessageId,
    Date joinedAt) {

  public static UserInfo from(ChatUser user) {
    Args.requireNotNull(user, "user");
    return new UserInfo(
        user.getUserId(),
        user.getIsCreator(),
        user.getLastMessageId(),
        user.getJoinedAt());
  }

}
