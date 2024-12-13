package com.vcb.backend.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/test.properties")
class PermissionEntityTest {

  @Test
  void onCreate_shouldSetDefaultValues() {
    Permission permission = new Permission();
    permission.onCreate();
    assertNotNull(permission.getPermissionCreatedAt());
    assertNotNull(permission.getPermissionUpdatedAt());
  }

  @Test
  void onUpdate_shouldSetDefaultValues() {
    Permission permission = new Permission();
    permission.onUpdate();
    assertNotNull(permission.getPermissionUpdatedAt());
  }
}
