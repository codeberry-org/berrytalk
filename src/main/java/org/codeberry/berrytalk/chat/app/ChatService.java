package org.codeberry.berrytalk.chat.app;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.codeberry.berrytalk.chat.domain.ChatUser;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.MessageRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

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

  public void addMessage(String userId, String chatId, MessageRequest messageRequest) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    ChatUser user = chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));

    messageRepository.save(createMessageByType(user, messageRequest));

    // send message to users
  }

  private Message createMessageByType(ChatUser user, MessageRequest messageRequest) {
    return switch (messageRequest.type()) {
      case TEXT -> user.createTextMessage(messageRequest.text());
      case MEDIA -> user.createMediaMessage(messageRequest.media());
    };
  }

  public void readMessage(String userId, String chatId, String messageId) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));
    
    chat.readMessage(userId, messageId);

    chatRepository.save(chat);
  }

  public List<MessageInfo> retrieveMessage(String userId, String chatId, int size) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));
    
    return messageRepository.findAll(chatId, size).stream()
        .map(MessageInfo::from)
        .toList();
  }

  public List<MessageInfo> retrieveMessage(String userId, String chatId, String prevMessageId, int size) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));
    
    return messageRepository.findAll(chatId, prevMessageId, size).stream()
        .map(MessageInfo::from)
        .toList();
  }

}
