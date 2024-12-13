package com.vcb.backend.repository;

import com.vcb.backend.entity.Article;
import com.vcb.backend.enums.ArticleStatusEnum;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecification {

  // Tìm kiếm bài viết theo từ khóa trong title, summary, content
  public static Specification<Article> hasKeyword(String keyword) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.isEmpty()) {
        return null; // Nếu không có từ khóa, không áp dụng điều kiện tìm kiếm
      }
      // Sử dụng LIKE để tìm kiếm trong các trường title, summary, content
      return criteriaBuilder.or(
        criteriaBuilder.like(criteriaBuilder.lower(root.get("articleTitle")), "%" + keyword.toLowerCase() + "%"),
        criteriaBuilder.like(criteriaBuilder.lower(root.get("articleSummary")), "%" + keyword.toLowerCase() + "%"),
        criteriaBuilder.like(criteriaBuilder.lower(root.get("articleContent")), "%" + keyword.toLowerCase() + "%")
      );
    };
  }

  // Lọc bài viết theo sectionName
  public static Specification<Article> hasSection(String sectionName) {
    return (root, query, criteriaBuilder) -> {
      if (sectionName == null || sectionName.isEmpty()) {
        return null; // Nếu không có sectionName, không áp dụng điều kiện lọc theo section
      }
      // Lọc theo sectionName
      return criteriaBuilder.equal(criteriaBuilder.lower(root.get("articleSectionName")), sectionName.toLowerCase());
    };
  }

  public static Specification<Article> hasStatus(ArticleStatusEnum status) {
    if (status == null) {
      return (root, query, criteriaBuilder) -> {
        return criteriaBuilder.equal(root.get("articleStatus"), ArticleStatusEnum.ACTIVE);
      }; // Nếu không có status, không áp dụng điều kiện lọc theo status
    }
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("articleStatus"), status);
    };
  }
}
