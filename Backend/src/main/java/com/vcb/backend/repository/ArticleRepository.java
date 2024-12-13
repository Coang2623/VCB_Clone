package com.vcb.backend.repository;

import com.vcb.backend.entity.Article;
import com.vcb.backend.enums.ArticleStatusEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  boolean existsByArticleTitle(String title);

  @Query("SELECT u FROM Article u WHERE u.articleStatus <> :status")
  List<Article> findAllArticlesWithStatusNot(@Param("status") ArticleStatusEnum status);

  @Query("SELECT u FROM Article u WHERE u.articleStatus <> :status AND u.articleId = :articleId")
  Article findArticlesByIdWithStatusNot(@Param("status") ArticleStatusEnum status, @Param("articleId") Long articleId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Article a WHERE a.articleUpdatedAt < :threshold AND a.articleStatus = 'DELETED'")
  void deleteArticlesOlderThan(@Param("threshold") LocalDate threshold);
}
