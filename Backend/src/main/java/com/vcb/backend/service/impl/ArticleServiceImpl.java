package com.vcb.backend.service.impl;

import com.vcb.backend.dto.ArticleDto;
import com.vcb.backend.entity.Article;
import com.vcb.backend.enums.ArticleStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.ArticleMapper;
import com.vcb.backend.repository.ArticleRepository;
import com.vcb.backend.repository.ArticleSpecification;
import com.vcb.backend.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private ArticleMapper articleMapper;


  /**
   * Method to get all articles with full info with pagination
   * @param page - request page
   * @param size - size of page
   * @return - Page of articles
   */
  @Override
  public Page<ArticleDto> getAllArticlesFullInfo(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Article> articles = articleRepository.findAll(pageable);
    return articles.map(article -> articleMapper.toArticleDtoFullInfo(article));
  }


  /**
   * Method to get all articles with basic info with pagination
   * @param page - request page
   * @param size - size of page
   * @return - Page of articles
   */
  @Override
  public Page<ArticleDto> getAllArticlesBasicInfo(int page, int size) {
    Specification<Article> specification = Specification.where(ArticleSpecification.hasStatus(ArticleStatusEnum.ACTIVE))
      .and(ArticleSpecification.hasStatus(ArticleStatusEnum.HIDDEN));

    Page<Article> articles = articleRepository.findAll(specification, PageRequest.of(page, size));

    return articles.map(article -> articleMapper.toArticleDtoBasicInfo(article));
  }


  /**
   * Method to get article by id with full info
   * @param articleId - id of article
   * @return - article with full info
   */
  @Override
  public ArticleDto getArticleByIdFullInfo(Long articleId) {

    Article article = articleRepository.findArticlesByIdWithStatusNot(ArticleStatusEnum.DELETED, articleId);

    if (article == null) {
      return null;
    }

    return articleMapper.toArticleDtoFullInfo(article);
  }


  /**
   * Method to get article by id with basic info
   * @param articleId - id of article
   * @return - article with basic info
   */
  @Override
  public ArticleDto getArticleByIdBasicInfo(Long articleId) {
    Article article = articleRepository.findArticlesByIdWithStatusNot(ArticleStatusEnum.DELETED, articleId);

    if (article == null) {
      return null;
    }

    return articleMapper.toArticleDtoBasicInfo(article);
  }

  /**
   * Method to  new article
   * @param articleDto - articleDto with create info
   * @return - article with full info
   */
  @Override
  public ArticleDto createArticle(ArticleDto articleDto) {

    if (articleRepository.existsByArticleTitle(articleDto.getArticleTitle())) {
      throw new AppException(AppError.ARTICLE_TITLE_EXISTED);
    }

    Article article = new Article();

    articleMapper.toCreateArticle(article, articleDto);

    article.setArticleStatus(ArticleStatusEnum.HIDDEN);

    return articleMapper.toArticleDtoFullInfo(articleRepository.save(article));
  }

  /**
   * Method to update article
   * @param articleDto - articleDto with update info
   * @return - article with full info
   */
  @Override
  public ArticleDto updateArticle(ArticleDto articleDto) {
    Article article = articleRepository.findById(articleDto.getArticleId()).orElseThrow( () -> new AppException(AppError.ARTICLE_ID_REQUIRED));

    if (articleRepository.existsByArticleTitle(articleDto.getArticleTitle()) && !article.getArticleTitle().equals(articleDto.getArticleTitle())) {
      throw new AppException(AppError.ARTICLE_TITLE_EXISTED);
    }

    articleMapper.toUpdateArticle(article, articleDto);

    return articleMapper.toArticleDtoFullInfo(articleRepository.save(article));
  }

  /**
   * Method to delete article
   * @param articleId - id of article
   */
  @Override
  public void deleteArticle(Long articleId) {
    Article article = articleRepository.findById(articleId).orElseThrow( () -> new AppException(AppError.ARTICLE_NOT_EXIST));

    article.setArticleStatus(ArticleStatusEnum.DELETED);

    articleRepository.save(article);
  }

  /**
   * Method to search article by keyword and filter by section
   * @param keyword - keyword for search
   * @param sectionName - section to filter article belong to
   * @param page - request page
   * @param size - size of page
   * @return - Page of articles
   */
  @Override
  public Page<ArticleDto> searchArticles(String keyword, String sectionName, int page, int size) {
    Specification<Article> specification = Specification.where(ArticleSpecification.hasKeyword(keyword))
                                                        .and(ArticleSpecification.hasSection(sectionName))
                                                        .and(ArticleSpecification.hasStatus(ArticleStatusEnum.ACTIVE)); // Lọc bài viết theo sectionName, keyword>
    return articleRepository.findAll(specification, PageRequest.of(page, size)).map(article -> articleMapper.toArticleDtoBasicInfo(article));
  }

  @Override
  public ArticleDto changeStatus(Long articleId, ArticleStatusEnum status) {
    Article article = articleRepository.findById(articleId).orElseThrow( () -> new AppException(AppError.ARTICLE_NOT_EXIST));

    article.setArticleStatus(status);

    return articleMapper.toArticleDtoBasicInfo(articleRepository.save(article));
  }
}
