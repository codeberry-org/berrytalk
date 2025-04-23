package org.codeberry.berrytalk.chat.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;
import org.codeberry.berrytalk.common.util.Args;

public class MediaMessage extends Message {
  private final List<Media> media;

  MediaMessage(String chatId, String userId, List<Media> media) {
    super(chatId, userId);
    this.media = Args.requireNotEmpty(media, "media");
  }

  public MediaMessage(String id, String chatId, String userId, Date createdAt, List<Media> media) {
    super(id, chatId, userId, createdAt);
    this.media = Args.requireNotEmpty(media, "media");
  }

  @Override
  public MessageType getType() {
    return MessageType.MEDIA;
  }

  @Override
  public String getTitle() {
    return String.format("%d media", media.size());
  }

  @Override
  public List<Media> getContent() {
    return Collections.unmodifiableList(media);
  }
  
}
