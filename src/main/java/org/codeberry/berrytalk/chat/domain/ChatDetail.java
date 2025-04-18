package org.codeberry.berrytalk.chat.domain;

import java.util.Date;
import java.util.List;

public record ChatDetail(
    String id,
    List<ChatUser> users,
    Date createdAt,
    String title,
    String imageId,
    Message lastMessage) {
}
