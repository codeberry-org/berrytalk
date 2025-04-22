package org.codeberry.berrytalk.chat.repository.entity;

import org.codeberry.berrytalk.chat.domain.MediaMessage;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity {
  private static ObjectMapper om = new ObjectMapper();
  public static enum Type {
    TEXT,
    MEDIA
  }

  @Id
  @Column(name = "id")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private ChatEntity chat;
  
  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "type", nullable = false)
  private Type type;

  @Column(name = "content", nullable = false)
  private String content;

  MessageEntity() {}

  private static MessageEntity fromMessage(Message message) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.id = message.getId();
    messageEntity.chat = new ChatEntity(message.getChatId());
    messageEntity.userId = message.getUserId();
    return messageEntity;
  }

  public static MessageEntity from(TextMessage message) {
    MessageEntity messageEntity = fromMessage(message);
    messageEntity.type = Type.TEXT;
    messageEntity.content = message.getText();
    return messageEntity;
  }

  public static MessageEntity from(MediaMessage message) {
    MessageEntity messageEntity = fromMessage(message);
    messageEntity.type = Type.MEDIA;
    try {
      messageEntity.content = om.writeValueAsString(message.getMedia());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return messageEntity;
  }

}
