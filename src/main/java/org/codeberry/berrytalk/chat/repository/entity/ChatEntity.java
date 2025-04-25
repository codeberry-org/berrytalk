package org.codeberry.berrytalk.chat.repository.entity;

import java.util.ArrayList;
import java.util.List;

import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatDetail;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "chat")
public class ChatEntity extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "chat_user", joinColumns = @JoinColumn(name = "chat_id"))
  private List<ChatUserValue> users = new ArrayList<>();

  @Column(name = "title", nullable = true)
  private String title;

  @Column(name = "image_id", nullable = true)
  private String imageId;

  @Transient
  private MessageEntity lastMessage;

  ChatEntity() {}

  ChatEntity(String id) {
    this.id = id;
  }

  public static ChatEntity from(Chat chat) {
    ChatEntity chatEntity = new ChatEntity(chat.getId());
    chatEntity.users = chat.getUsers().stream()
        .map(ChatUserValue::new)
        .toList();
    chatEntity.title = chat.getTitle();
    chatEntity.imageId = chat.getImageId();
    return chatEntity;
  }

  public Chat toChat() {
    return new Chat(
        id,
        users.stream().map(u -> u.toChatUser(id)).toList(),
        getCreatedAt(),
        title,
        imageId);
  }

  public ChatDetail toChatDetail() {
    return new ChatDetail(
        id,
        users.stream().map(u -> u.toChatUser(id)).toList(),
        getCreatedAt(),
        title,
        imageId,
        lastMessage != null ? lastMessage.toMessage() : null);
  }
}
