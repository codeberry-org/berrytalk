package org.codeberry.berrytalk.chat.repository.entity;

import java.util.Date;

import org.codeberry.berrytalk.chat.domain.ChatUser;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class ChatUserValue {
  @Embedded
  private ChatEntity chat;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "is_creator", nullable = false)
  private Boolean isCreator;

  @Column(name = "joined_at", nullable = false)
  private Date joinedAt;

  @Column(name = "last_message_id", nullable = true)
  private String lastMessageId;

  ChatUserValue() {}

  ChatUserValue(ChatEntity chatEntity, ChatUser chatUser) {
    this.chat = chatEntity;
    this.userId = chatUser.getUserId();
    this.isCreator = chatUser.getIsCreator();
    this.joinedAt = chatUser.getJoinedAt();
    this.lastMessageId = chatUser.getLastMessageId();
  }

  public ChatUser toChatUser() {
    return new ChatUser(
        chat.getId(),
        userId,
        isCreator,
        joinedAt,
        lastMessageId);
  }
}
