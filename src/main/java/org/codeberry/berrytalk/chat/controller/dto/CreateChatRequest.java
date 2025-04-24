package org.codeberry.berrytalk.chat.controller.dto;

import java.util.List;

public record CreateChatRequest(
    List<String> inviteeIds) {

}
