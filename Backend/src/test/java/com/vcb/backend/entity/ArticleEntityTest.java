package com.vcb.backend.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/test.properties")
class ArticleEntityTest {

  @Test
  void onCreate_shouldSetDefaultValues() {
    Article article = new Article();
    article.onCreate();
    assertNotNull(article.getArticleCreatedAt());
    assertNotNull(article.getArticleUpdatedAt());
  }

  @Test
  void onUpdate_shouldSetDefaultValues() {
    Article article = new Article();
    article.onUpdate();
    assertNotNull(article.getArticleUpdatedAt());
  }
}
