package org.codeberry.berrytalk.chat.repository.entity;

import org.codeberry.berrytalk.chat.domain.MediaMessage;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.TextMessage;

import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.List;

import org.codeberry.berrytalk.chat.common.model.Media;
import org.codeberry.berrytalk.chat.common.model.MessageType;
import org.codeberry.berrytalk.chat.common.util.JsonUtil;

@Getter
@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private ChatEntity chat;
  
  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "type", nullable = false)
  private MessageType type;

  @Column(name = "content", nullable = false)
  private String content;

  MessageEntity() {}

  MessageEntity(String id) {
    this.id = id;
  }

  public static MessageEntity from(Message message) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.id = message.getId();
    messageEntity.chat = new ChatEntity(message.getChatId());
    messageEntity.userId = message.getUserId();
    messageEntity.type = message.getType();
    messageEntity.content = JsonUtil.toJson(message.getContent());
    return messageEntity;
  }

  public Message toMessage() {
    return switch(type) {
      case TEXT -> toTextMessage();
      case MEDIA -> toMediaMessage();
    };
  }

  private TextMessage toTextMessage() {
    return new TextMessage(
        id,
        chat.getId(),
        userId,
        getCreatedAt(),
        content);
  }

  private MediaMessage toMediaMessage() {
    return new MediaMessage(
        id,
        chat.getId(),
        userId,
        getCreatedAt(),
        JsonUtil.fromJson(content, new TypeReference<List<Media>>() {}));
  }
}
