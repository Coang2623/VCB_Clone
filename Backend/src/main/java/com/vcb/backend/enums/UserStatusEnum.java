package com.vcb.backend.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
  ACTIVE("ACTIVE"),
  DISABLED("DISABLED"),
  DELETED("DELETED");

  private String userStatus;

  private UserStatusEnum(String userStatus) {
    this.userStatus = userStatus;
  }

}
