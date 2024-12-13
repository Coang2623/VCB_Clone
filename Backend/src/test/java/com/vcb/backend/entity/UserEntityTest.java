package com.vcb.backend.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserEntityTest {
  @Test
  void onCreate_shouldSetDefaultValues() {
    User user = new User();
    user.onCreate();

    assertNotNull(user.getUserCreatedAt());
    assertNotNull(user.getUserUpdatedAt());
  }

  @Test
  void onUpdate_shouldSetDefaultValues() {
    User user = new User();
    user.onUpdate();

    assertNotNull(user.getUserUpdatedAt());
  }
}
