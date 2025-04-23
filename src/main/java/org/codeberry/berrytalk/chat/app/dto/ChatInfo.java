package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.common.util.Args;
import org.codeberry.berrytalk.chat.domain.Chat;

public record ChatInfo(
  String id,
  List<UserInfo> users,
  String title,
  String imageId,
  Date createdAt
) {
  
  public static ChatInfo from(Chat chat) {
    Args.requireNotNull(chat, "chat");
    return new ChatInfo(
        chat.getId(),
        chat.getUsers().stream().map(UserInfo::from).toList(),
        chat.getTitle(),
        chat.getImageId(),
        chat.getCreatedAt());
  }
}
