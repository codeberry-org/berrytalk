package org.codeberry.berrytalk.chat.repository.entity;

import java.util.Date;

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
}
