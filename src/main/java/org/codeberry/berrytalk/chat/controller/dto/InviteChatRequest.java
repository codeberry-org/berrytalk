package org.codeberry.berrytalk.chat.controller.dto;

import java.util.List;

public record InviteChatRequest(
    List<String> inviteeIds) {

}
