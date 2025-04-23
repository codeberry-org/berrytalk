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
public class SessionManager {
  private final Map<String, Set<Session>> sessions = new HashMap<>();
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  
  public SessionManager() {
  }

  public void add(Session session) {
    String userId = session.getUserId();
    lock.writeLock().lock();
    try {
      Set<Session> userSessions = sessions.getOrDefault(userId, new HashSet<>());
      userSessions.add(session);
      sessions.put(userId, userSessions);
    } finally {
      lock.writeLock().unlock();
    }
    session.onClose(this::remove);
  }

  private synchronized void remove(Session session) {
    String userId = session.getUserId();
    lock.writeLock().lock();
    try {
      Set<Session> userSessions = sessions.get(userId);
      if (userSessions != null) {
        userSessions.remove(session);
        if (userSessions.isEmpty()) {
          sessions.remove(userId);
        }
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Set<String> sendMessage(Collection<String> userIds, Message message) {
    Set<String> successIds = new HashSet<>();
    List<Session> foundSessions;

    lock.readLock().lock();
    try {
      foundSessions = userIds.stream()
          .flatMap(u -> sessions.getOrDefault(u, Collections.emptySet()).stream())
          .toList();
    } finally {
      lock.readLock().unlock();
    }

    for (Session session: foundSessions) {
      String userId = session.getUserId();
      String deviceId = session.getDeviceId();
      try {
        session.sendMessage(message);
        successIds.add(userId);
      } catch (Exception ex) {
        log.error(String.format("Failed to send message to (%s/%s) - %s", userId, deviceId, ex.getMessage()), ex);
        session.close();
      }
    }

    return successIds;
  }
}
