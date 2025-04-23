package org.codeberry.berrytalk.chat.app;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;
import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.codeberry.berrytalk.chat.domain.ChatUser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
  private final ChatRepository chatRepository;

  public ChatInfo createChat(String userId) {
    Chat chat = new Chat(userId);

    chatRepository.save(chat);

    return ChatInfo.from(chat);
  }

  public ChatInfo updateChatTitle(String userId, String chatId, String title) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    ChatUser owner = chat.getOwner()
        .orElseThrow(() -> new RuntimeException("Owner of the chat not found"));
    if (!owner.getUserId().equals(userId)) {
      throw new RuntimeException("Only owner can update title of chat");
    }

    chat.updateTitle(title);
    chatRepository.save(chat);

    return ChatInfo.from(chat);
  }

  public ChatInfo updateChatImage(String userId, String chatId, String imageId) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    ChatUser owner = chat.getOwner()
        .orElseThrow(() -> new RuntimeException("Owner of the chat not found"));
    if (!owner.getUserId().equals(userId)) {
      throw new RuntimeException("Only owner can update image of chat");
    }

    chat.updateImageId(imageId);
    chatRepository.save(chat);

    return ChatInfo.from(chat);
  }

  public ChatInfo joinChat(String userId, String chatId) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));

    chat.addUser(userId);
    chatRepository.save(chat);

    return ChatInfo.from(chat);
  }

  public ChatInfo leaveChat(String userId, String chatId) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));

    chat.removeUser(userId);
    chatRepository.save(chat);

    return ChatInfo.from(chat);
  }

  public List<ChatDetailInfo> retrieveChat(String userId, Date updatedAfter) {
    return chatRepository.findAllDetailByUser(userId, updatedAfter).stream()
        .map(ChatDetailInfo::from)
        .toList();
  }

}
