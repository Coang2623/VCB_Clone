package com.vcb.backend.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/test.properties")
class RoleEntityTest {
  @Test
  void onCreate_shouldSetDefaultValues() {
    Role role = new Role();
    role.onCreate();
    assertNotNull(role.getRoleCreatedAt());
    assertNotNull(role.getRoleUpdatedAt());
  }

  @Test
  void onUpdate_shouldSetDefaultValues() {
    Role role = new Role();
    role.onUpdate();
    assertNotNull(role.getRoleUpdatedAt());
  }
}
