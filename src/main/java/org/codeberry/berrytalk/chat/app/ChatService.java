package org.codeberry.berrytalk.chat.app;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codeberry.berrytalk.chat.app.dto.ChatDetailInfo;
import org.codeberry.berrytalk.chat.app.dto.ChatInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageInfo;
import org.codeberry.berrytalk.chat.app.dto.MessageRequest;
import org.codeberry.berrytalk.chat.domain.Chat;
import org.codeberry.berrytalk.chat.domain.ChatRepository;
import org.codeberry.berrytalk.chat.domain.ChatSession;
import org.codeberry.berrytalk.chat.domain.ChatSessionService;
import org.codeberry.berrytalk.chat.domain.ChatUser;
import org.codeberry.berrytalk.chat.domain.Message;
import org.codeberry.berrytalk.chat.domain.MessageRepository;
import org.codeberry.berrytalk.chat.domain.NotificationService;
import org.codeberry.berrytalk.chat.domain.RelayService;
import org.codeberry.berrytalk.chat.domain.event.ChatEvent;
import org.codeberry.berrytalk.chat.domain.event.NewMessageEvent;
import org.codeberry.berrytalk.chat.domain.event.SeenMessageEvent;
import org.codeberry.berrytalk.chat.domain.event.UpdateChatEvent;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final ChatSessionService chatSessionService;
  private final RelayService relayService;
  private final NotificationService notificationService;

  public ChatInfo createChat(String ownerId, Collection<String> inviteeIds) {
    Chat chat = new Chat(ownerId);
    chat.addUsers(inviteeIds);

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

    List<String> userIds = extractUserIds(chat);
    UpdateChatEvent updateChatEvent = UpdateChatEvent.fromTitle(chatId, title);

    broadcastChatEventOnline(userIds, updateChatEvent);

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

    List<String> userIds = extractUserIds(chat);
    UpdateChatEvent updateChatEvent = UpdateChatEvent.fromImageId(chatId, imageId);

    broadcastChatEventOnline(userIds, updateChatEvent);

    return ChatInfo.from(chat);
  }

  public ChatInfo inviteChat(String userId, String chatId, Collection<String> inviteeIds) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("Not a chat member"));

    chat.addUsers(inviteeIds);
    chatRepository.save(chat);

    List<String> userIds = extractUserIds(chat);
    List<ChatUser> users = chat.getUsers();
    UpdateChatEvent updateChatEvent = UpdateChatEvent.fromUsers(chatId, users);

    broadcastChatEventOnline(userIds, updateChatEvent);

    return ChatInfo.from(chat);
  }

  public ChatInfo leaveChat(String userId, String chatId) {
    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));

    chat.removeUser(userId);
    chatRepository.save(chat);

    List<String> userIds = extractUserIds(chat);
    List<ChatUser> users = chat.getUsers();
    UpdateChatEvent updateChatEvent = UpdateChatEvent.fromUsers(chatId, users);

    broadcastChatEventOnline(userIds, updateChatEvent);

    return ChatInfo.from(chat);
  }

  public List<ChatDetailInfo> retrieveChat(String userId, Date updatedAfter) {
    return chatRepository.findAllDetailByUser(userId, updatedAfter).stream()
        .map(ChatDetailInfo::from)
        .toList();
  }

  public void registerMessageSession(ChatSession session) {
    chatSessionService.add(session);
  }

  public void deregisterMessageSession(String sessionId) {
    chatSessionService.remove(sessionId);
  }

  public void addMessage(String userId, String chatId, MessageRequest messageRequest) {
    log.info("add message: userId({}), chatId({}), messageRequest({})", userId, chatId, messageRequest);

    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    ChatUser user = chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));

    Message message = createMessageByType(user, messageRequest);

    messageRepository.save(message);

    List<String> userIds = extractUserIds(chat);
    NewMessageEvent newMessageEvent = new NewMessageEvent(chatId, message);

    Set<String> sentUserIds = broadcastChatEventOnline(userIds, newMessageEvent);

    List<String> remainUserIds = userIds.stream()
        .filter(u -> !sentUserIds.contains(u))
        .toList();
    broadcaseChatEventOffline(remainUserIds, newMessageEvent);
  }
  
  private Message createMessageByType(ChatUser user, MessageRequest messageRequest) {
    return switch (messageRequest.type()) {
      case TEXT -> user.createTextMessage(messageRequest.text());
      case MEDIA -> user.createMediaMessage(messageRequest.media());
    };
  }

  public void readMessage(String userId, String chatId, String messageId) {
    log.info("read message: userId({}), chatId({}), messageId({})", userId, chatId, messageId);

    Chat chat = chatRepository.findById(chatId)
        .orElseThrow(() -> new RuntimeException("Chat not exist"));
    chat.getUser(userId)
        .orElseThrow(() -> new RuntimeException("User is not a member of the chat"));
    
    chat.readMessage(userId, messageId);

    chatRepository.save(chat);

    broadcastChatEventOnline(extractUserIds(chat), new SeenMessageEvent(chatId, userId, messageId));
  }

  private List<String> extractUserIds(Chat chat) {
    return chat.getUsers().stream()
        .map(ChatUser::getUserId)
        .toList();
  }

  private Set<String> broadcastChatEventOnline(Collection<String> userIds, ChatEvent event) {
    Set<String> sentUserIds = chatSessionService.sendChatEvent(userIds, event);
    log.debug("sentUserIds: {}", sentUserIds);

    Set<String> relayedUserIds = relayService.relayChatEvent(userIds, event);
    log.debug("relayedUserIds: {}", relayedUserIds);

    sentUserIds.addAll(relayedUserIds);
    log.info("Success to send event({}) to {} online users", event, sentUserIds.size());

    return sentUserIds;
  }

  private Set<String> broadcaseChatEventOffline(Collection<String> userIds, ChatEvent event) {
    Set<String> notifiedUserIds = notificationService.notifyChatEvent(userIds, event);
    log.info("Success to notify event({}) to {} offline users", event, notifiedUserIds.size());

    return notifiedUserIds;
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
