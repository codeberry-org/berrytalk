package org.codeberry.berrytalk.chat.repository.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;

@Getter
@Entity
@Table(name = "chat")
public class ChatEntity extends BaseEntity {
  @Id
  @Column(name = "id")
  private String id;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "chat_user", joinColumns = @JoinColumn(name = "chat_id"))
  @OrderColumn(name = "joined_at")
  private List<ChatUserValue> users;

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
}
