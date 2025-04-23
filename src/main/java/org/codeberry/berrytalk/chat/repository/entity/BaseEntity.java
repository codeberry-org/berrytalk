package org.codeberry.berrytalk.chat.repository.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private Date createdAt;

  @LastModifiedDate
  @Column(name = "modified_at")
  private Date modifiedAt;
}
