package com.vcb.backend.entity;

import com.vcb.backend.enums.ArticleStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    this.articleCreatedBy = principal.getClaim("username");
    this.articleCreatedAt = LocalDateTime.now();
    this.articleUpdatedAt = LocalDateTime.now();
    this.articleUpdatedBy = principal.getClaim("username");
  }

  @PreUpdate
  public void onUpdate() {
    Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    this.articleUpdatedBy = principal.getClaim("username");
    this.articleUpdatedAt = LocalDateTime.now();
  }
}
