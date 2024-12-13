package com.vcb.backend.entity;

import com.vcb.backend.enums.ArticleStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long articleId;

  @Column(nullable = false, unique = true, length = 100)
  String articleTitle;

  @Column(nullable = false, length = 1000)
  String articleSummary;

  @Column(nullable = false, length = 50)
  String articleAuthor;

  @Column(nullable = false, length = 50)
  String articleSectionName;

  @Column(nullable = false, length = 5000)
  String articleContent;

  @Enumerated(EnumType.STRING)
  private ArticleStatusEnum articleStatus;

  @Column(updatable = false)
  LocalDateTime articleCreatedAt;

  @Column(updatable = false)
  String articleCreatedBy;

  LocalDateTime articleUpdatedAt;

  String articleUpdatedBy;

  @PrePersist
  public void onCreate() {
    this.articleCreatedAt = LocalDateTime.now();
    this.articleUpdatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    this.articleUpdatedAt = LocalDateTime.now();
  }
}
