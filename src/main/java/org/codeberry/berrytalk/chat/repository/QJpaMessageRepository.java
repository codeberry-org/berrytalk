package org.codeberry.berrytalk.chat.repository;

import static org.codeberry.berrytalk.chat.repository.entity.QMessageEntity.messageEntity;

import java.util.List;

import org.codeberry.berrytalk.chat.repository.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QJpaMessageRepository {
  private final JPAQueryFactory jpaQueryFactory;

  public List<MessageEntity> findAll(String chatId, int size) {
    return jpaQueryFactory
        .select(messageEntity)
        .from(messageEntity)
        .where(messageEntity.chat.id.eq(chatId))
        .offset(size)
        .fetch();
  }

  public List<MessageEntity> findAll(String chatId, String prevMessageId, int size) {
    return jpaQueryFactory
        .select(messageEntity)
        .from(messageEntity)
        .where(messageEntity.chat.id.eq(chatId),
            messageEntity.id.gt(prevMessageId))
        .offset(size)
        .fetch();
  }
}
