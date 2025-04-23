package org.codeberry.berrytalk.chat.app.dto;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.domain.ChatDetail;

public record ChatDetailInfo(
    String id,
    List<UserInfo> users,
    String title,
    String imageId,
    MessageInfo lastMessage,
    Date createdAt) {

  public static ChatDetailInfo from(ChatDetail chatDetail) {
    if (chatDetail == null) {
      return null;
    }
    return new ChatDetailInfo(
        chatDetail.id(),
        chatDetail.users().stream().map(UserInfo::from).toList(),
        chatDetail.title(),
        chatDetail.imageId(),
        MessageInfo.from(chatDetail.lastMessage()),
        chatDetail.createdAt());
  }
}
