package com.vcb.backend.dto;

import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.enums.ArticleStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDto {
  @NotNull(message = "ARTICLE_ID_REQUIRED", groups = {UpdateGroup.class})
  private Long articleId;
  @NotBlank(message = "ARTICLE_TITLE_REQUIRED", groups = {CreateGroup.class})
  private String articleTitle;
  @NotBlank(message = "ARTICLE_SUMMARY_REQUIRED", groups = {CreateGroup.class})
  private String articleSummary;
  @NotBlank(message = "ARTICLE_AUTHOR_REQUIRED", groups = {CreateGroup.class})
  private String articleAuthor;

  private String articleSectionName;
  @NotBlank(message = "ARTICLE_CONTENT_REQUIRED", groups = {CreateGroup.class})
  private String articleContent;


  private ArticleStatusEnum articleStatus;

  private LocalDateTime articleCreatedAt;

  private String articleCreatedBy;

  private LocalDateTime articleUpdatedAt;

  private String articleUpdatedBy;

}
