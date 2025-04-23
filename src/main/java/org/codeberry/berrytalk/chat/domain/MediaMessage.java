package org.codeberry.berrytalk.chat.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.common.model.Media;

public class MediaMessage extends Message {
  private final List<Media> media;

  MediaMessage(String chatId, String userId, List<Media> media) {
    super(chatId, userId);
    this.media = media;
  }

  public MediaMessage(String id, String chatId, String userId, Date createdAt, List<Media> media) {
    super(id, chatId, userId, createdAt);
    this.media = media;
  }

  public List<Media> getMedia() {
    return Collections.unmodifiableList(media);
  }

  @Override
  public String getTitle() {
    return "Media";
  }
  
}
