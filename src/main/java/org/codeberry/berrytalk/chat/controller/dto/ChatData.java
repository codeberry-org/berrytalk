package org.codeberry.berrytalk.chat.controller.dto;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatData(
    String id,
    List<ChatUserData> users,
    String title,
    String imageId,
    MessageData lastMessage,
    Date createdAt) {
  
  public static ChatData from(ChatInfo chatInfo) {
    if (chatInfo == null) {
      return null;
    }
    return new ChatData(
        chatInfo.id(),
        chatInfo.users().stream().map(ChatUserData::from).toList(),
        chatInfo.title(),
        chatInfo.imageId(),
        null,
        chatInfo.createdAt());
  }

  public static ChatData from(ChatDetailInfo chatDetailInfo) {
    if (chatDetailInfo == null) {
      return null;
    }
    return new ChatData(
        chatDetailInfo.id(),
        chatDetailInfo.users().stream().map(ChatUserData::from).toList(),
        chatDetailInfo.title(),
        chatDetailInfo.imageId(),
        MessageData.from(chatDetailInfo.lastMessage()),
        chatDetailInfo.createdAt());
  }
}
