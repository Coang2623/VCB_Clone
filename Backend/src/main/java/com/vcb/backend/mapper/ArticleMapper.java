package com.vcb.backend.mapper;

import com.vcb.backend.dto.ArticleDto;
import com.vcb.backend.dto.SectionDto;
import com.vcb.backend.entity.Article;
import com.vcb.backend.entity.Section;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

  @Mapping(target = "articleId", ignore = true)
  @Mapping(target = "articleStatus", ignore = true)
  void toCreateArticle(@MappingTarget Article article , ArticleDto articleDto);


  @Mapping(target = "articleStatus", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdateArticle(@MappingTarget Article article , ArticleDto articleDto);


  ArticleDto toArticleDtoFullInfo(Article article);

  @Mapping(target = "articleCreatedAt", ignore = true)
  @Mapping(target = "articleCreatedBy", ignore = true)
  @Mapping(target = "articleUpdatedAt", ignore = true)
  @Mapping(target = "articleUpdatedBy", ignore = true)
  @Mapping(target = "articleContent", ignore = true)
  ArticleDto toArticleDtoBasicInfo(Article article);
}
