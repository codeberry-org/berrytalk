package org.codeberry.berrytalk.chat.controller.dto;

import java.util.Date;

import org.codeberry.berrytalk.chat.app.dto.ChatUserInfo;
import org.codeberry.berrytalk.chat.domain.ChatUser;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatUserData(
    String id,
    boolean isCreator,
    String lastMessageId,
    Date joinedAt) {

  public static ChatUserData from(ChatUserInfo chatUserInfo) {
    if (chatUserInfo == null) {
      return null;
    }
    return new ChatUserData(
        chatUserInfo.id(),
        chatUserInfo.isCreator(),
        chatUserInfo.lastMessageId(),
        chatUserInfo.joinedAt());
  }

  public static ChatUserData from(ChatUser chatUser) {
    return from(ChatUserInfo.from(chatUser));
  }
}
