package org.codeberry.berrytalk.chat.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.codeberry.berrytalk.chat.domain.exception.AlreadyExistException;
import org.codeberry.berrytalk.common.util.Args;
import org.codeberry.berrytalk.common.util.IdUtil;

public class Chat {
  public static final String ID_PREFIX = "chat";

  private final String id;
  private final List<ChatUser> users;
  private final Date createdAt;

  private String title;
  private String imageId;

  public Chat(String userId) {
    this.id = IdUtil.create(ID_PREFIX);
    this.users = new ArrayList<>(Arrays.asList(new ChatUser(id, userId, true)));
    this.createdAt = new Date();
  }

  public Chat(String id, List<ChatUser> users, Date createdAt, String title, String imageId) {
    this.id = Args.requireNotEmpty(id, "id");
    this.users = Args.requireNotNull(users, "user");
    this.createdAt = Args.requireNotNull(createdAt, "createdAt");
    this.title = title;
    this.imageId = imageId;
  }

  public String getId() {
    return id;
  }

  public ChatUser addUser(String userId) {
    if (users.stream().anyMatch(user -> user.getUserId().equals(userId))) {
      throw new AlreadyExistException("User already in chat");
    }
    ChatUser user = new ChatUser(id, userId, false);
    users.add(user);
    return user;
  }

  public List<ChatUser> addUsers(Collection<String> userIds) {
    return userIds.stream().map(this::addUser).toList();
  }

  public boolean removeUser(String userId) {
    return users.removeIf(user -> user.getUserId().equals(userId));
  }

  public Optional<ChatUser> getUser(String userId) {
    return users.stream()
        .filter(user -> user.getUserId().equals(userId))
        .findFirst();
  }

  public Optional<ChatUser> getOwner() {
    return users.stream()
        .filter(ChatUser::getIsCreator)
        .findFirst()
        .or(() -> users.stream()
            .sorted(Comparator.comparing(ChatUser::getJoinedAt))
            .findFirst());
  }

  public List<ChatUser> getUsers() {
    return Collections.unmodifiableList(users);
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void updateImageId(String imageId) {
    this.imageId = imageId;
  }

  public String getImageId() {
    return imageId;
  }

  public void readMessage(String userId, String messageId) {
    users.stream()
        .filter(u -> u.getUserId().equals(userId))
        .findFirst()
        .ifPresent(u -> {
          u.updateLastMessageId(messageId);
        });
  }

}
