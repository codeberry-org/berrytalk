package org.codeberry.berrytalk.chat.controller.dto;

import java.util.List;

import org.codeberry.berrytalk.common.model.Media;
import org.codeberry.berrytalk.common.model.MessageType;

public record AddMessage(
    String chatId,
    MessageType type,
    String text,
    List<Media> media) {
}
