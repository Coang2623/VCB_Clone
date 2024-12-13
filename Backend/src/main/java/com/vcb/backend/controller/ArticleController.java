package com.vcb.backend.controller;

import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.ArticleDto;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.enums.ArticleStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
  @Autowired
  private ArticleServiceImpl articleService;

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @GetMapping("/all")
  public ApiResponse<Page<ArticleDto>> getAllArticlesWithFullInfo(@RequestParam int page, @RequestParam int size) {
    return ApiResponse.<Page<ArticleDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all Article with full info successfully")
      .result(articleService.getAllArticlesFullInfo(page, size))
      .build();
  }

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @GetMapping("/all-basic")
  public ApiResponse<Page<ArticleDto>> getAllArticlesBasicInfo(@RequestParam int page, @RequestParam int size) {
    return ApiResponse.<Page<ArticleDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all Article with basic info successfully")
      .result(articleService.getAllArticlesBasicInfo(page, size))
      .build();
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/search")
  public ApiResponse<Page<ArticleDto>> searchArticles(@RequestParam(value = "keyword" ,required = false) String keyword, @RequestParam(value = "sectionName", required = false) String sectionName, @RequestParam int page, @RequestParam int size) {
    return ApiResponse.<Page<ArticleDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Search articles by keyword " + keyword + ", section " + sectionName + " successfully")
      .result(articleService.searchArticles(keyword, sectionName, page, size))
      .build();
  }

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @PutMapping
  public ApiResponse<ArticleDto> updateArticle(@RequestBody @Validated(UpdateGroup.class) ArticleDto articleDto) {
    return ApiResponse.<ArticleDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Update article successfully")
      .result(articleService.updateArticle(articleDto))
      .build();
  }

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @PostMapping
  public ApiResponse<ArticleDto> createArticle(@RequestBody @Validated(CreateGroup.class) ArticleDto articleDto) {
    return ApiResponse.<ArticleDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Create article successfully")
      .result(articleService.createArticle(articleDto))
      .build();
  }

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @DeleteMapping("/{articleId}")
  public ApiResponse<Void> deleteArticle(@PathVariable("articleId") Long articleId) {
    articleService.deleteArticle(articleId);
    return ApiResponse.<Void>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Delete article successfully")
      .build();
  }

  @PreAuthorize("hasRole('ARTICLE_MANAGER') or hasRole('ADMIN')")
  @PutMapping("/change-status/{articleId}/{status}")
  public ApiResponse<ArticleDto> changeStatus(@PathVariable("articleId") Long articleId, @PathVariable("status") ArticleStatusEnum status) {
    return ApiResponse.<ArticleDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Change article status successfully")
      .result(articleService.changeStatus(articleId, status))
      .build();
  }
}
