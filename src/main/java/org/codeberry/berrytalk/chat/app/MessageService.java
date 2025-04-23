package org.codeberry.berrytalk.chat.app;

import java.util.List;
import java.util.Set;

import org.codeberry.berrytalk.chat.app.dto.MessageInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.codeberry.berrytalk.chat.domain.ChatUser;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.MessageRepository;
import org.codeberry.berrytalk.chat.domain.MessageSession;
import org.codeberry.berrytalk.chat.domain.MessageSessionService;
import org.codeberry.berrytalk.chat.domain.NotificationService;
import org.codeberry.berrytalk.chat.domain.RelayService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final MessageSessionService messageSessionService;
  private final RelayService relayService;
  private final NotificationService notificationService;
  
  public void registerMessageSession(MessageSession session) {
    messageSessionService.add(session);
  }

  public void deregisterMessageSession(String sessionId) {
    messageSessionService.remove(sessionId);
  }

  public void addMessage(String userId, String chatId, MessageRequest messageRequest) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    ChatUser user = chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));

    Message message = createMessageByType(user, messageRequest);

    messageRepository.save(message);

    List<String> userIds = chat.getUsers().stream()
        .map(ChatUser::getUserId)
        .toList();
    log.info("userIds: {}", userIds);

    Set<String> sentUserIds = messageSessionService.sendMessage(userIds, message);
    log.info("sentUserIds: {}", sentUserIds);

    Set<String> relayedUserIds = relayService.relayMessage(userIds, message);
    log.info("relayedUserIds: {}", relayedUserIds);

    List<String> offlineUserIds = userIds.stream()
        .filter(u -> !sentUserIds.contains(u))
        .filter(u -> !relayedUserIds.contains(u))
        .toList();
    Set<String> notifiedUserIds = notificationService.notifyMessage(offlineUserIds, message);
    log.info("notifiedUserIds: {}", notifiedUserIds);
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
