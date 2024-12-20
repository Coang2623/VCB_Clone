package com.vcb.backend.service;

import com.vcb.backend.dto.ArticleDto;
import com.vcb.backend.enums.ArticleStatusEnum;
import org.springframework.data.domain.Page;

public interface ArticleService {
  public Page<ArticleDto> getAllArticlesFullInfo(int page, int size);

  public Page<ArticleDto> getAllArticlesBasicInfo(int page, int size);

  public ArticleDto getArticleByIdFullInfo(Long articleId);

  public ArticleDto getArticleByIdBasicInfo(Long articleId);

  public ArticleDto createArticle(ArticleDto articleDto);

  public ArticleDto updateArticle(ArticleDto articleDto);

  public void deleteArticle(Long articleId);

  public Page<ArticleDto> searchArticles(String keyword, String sectionName, int page, int size);

  public ArticleDto changeStatus(Long articleId, ArticleStatusEnum status);
}
