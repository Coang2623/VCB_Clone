package com.vcb.backend.enums;

import lombok.Getter;

@Getter
public enum ArticleStatusEnum {
  ACTIVE("ACTIVE"),
  HIDDEN("HIDDEN"),
  DELETED("DELETED");

  private String articleStatus;

  private ArticleStatusEnum(String userStatus) {
    this.articleStatus = userStatus;
  }

}
