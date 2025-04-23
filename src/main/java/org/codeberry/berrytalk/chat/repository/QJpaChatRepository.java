package org.codeberry.berrytalk.chat.repository;

import static org.codeberry.berrytalk.chat.repository.entity.QChatEntity.chatEntity;
import static org.codeberry.berrytalk.chat.repository.entity.QMessageEntity.messageEntity;

import java.util.Date;
import java.util.List;

import org.codeberry.berrytalk.chat.repository.entity.ChatEntity;
import org.codeberry.berrytalk.chat.repository.entity.QMessageEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QJpaChatRepository {
  private final JPAQueryFactory jpaQueryFactory;

  List<ChatEntity> findAllDetailByUser(String userId, Date updatedAfter) {
    QMessageEntity messageEntity2 = new QMessageEntity("messageEntity2"); // Alias for subquery

    List<Tuple> results = jpaQueryFactory
        .select(chatEntity, messageEntity)
        .from(chatEntity)
        .join(messageEntity).on(chatEntity.id.eq(messageEntity.chat.id))
        .where(
            // Condition 1: The chat must contain the specified userId
            chatEntity.users.any().userId.eq(userId),
            // Condition 2: The joined message must be the latest one for that chat
            messageEntity.createdAt.eq(
                JPAExpressions.select(messageEntity2.createdAt.max())
                    .from(messageEntity2)
                    .where(messageEntity2.chat.id.eq(chatEntity.id))
            ),
            // Condition 3: The latest message must be created after updatedAfter
            messageEntity.createdAt.after(updatedAfter))
        .fetch();

    return results.stream()
        .map(tuple -> {
          ChatEntity chat = tuple.get(chatEntity);
          chat.setLastMessage(tuple.get(messageEntity));
          return chat;
        })
        .toList();
  }
}
