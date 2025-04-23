package org.codeberry.berrytalk.chat.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSessionService {
  private final Map<String, MessageSession> sessions = new HashMap<>();
  private final Map<String, Set<MessageSession>> userSessions = new HashMap<>();
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  
  public MessageSessionService() {
  }

  public void add(MessageSession session) {
    lock.writeLock().lock();
    try {
      sessions.put(session.getId(), session);
      String userId = session.getUserId();
      Set<MessageSession> sessions = userSessions.getOrDefault(userId, new HashSet<>());
      sessions.add(session);
      userSessions.put(userId, sessions);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public void remove(String sessionId) {
    lock.writeLock().lock();
    try {
      MessageSession session = sessions.remove(sessionId);
      if (session != null) {
        String userId = session.getUserId();
        Set<MessageSession> sessions = userSessions.get(userId);
        if (sessions != null) {
          sessions.remove(session);
          if (sessions.isEmpty()) {
            userSessions.remove(userId);
          }
        }
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Set<String> sendMessage(Collection<String> userIds, Message message) {
    Set<String> successIds = new HashSet<>();
    List<MessageSession> foundSessions;

    lock.readLock().lock();
    try {
      foundSessions = userIds.stream()
          .flatMap(u -> userSessions.getOrDefault(u, Collections.emptySet()).stream())
          .toList();
    } finally {
      lock.readLock().unlock();
    }

    for (MessageSession session: foundSessions) {
      String userId = session.getUserId();
      String deviceId = session.getDeviceId();
      try {
        session.sendMessage(message);
        successIds.add(userId);
      } catch (Exception ex) {
        log.error(String.format("Failed to send message to (%s/%s) - %s", userId, deviceId, ex.getMessage()), ex);
        closeSession(session);
      }
    }

    return successIds;
  }

  private void closeSession(MessageSession session) {
    remove(session.getId());
    try {
      session.close();
    } catch (Exception ex) {
      log.warn(String.format("Failed to close session(%s) - %s", session.getId(), ex.getMessage()), ex);
    }
  }
}
