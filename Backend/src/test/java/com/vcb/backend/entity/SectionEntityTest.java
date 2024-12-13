package com.vcb.backend.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/test.properties")
class SectionEntityTest {

  @Test
  void onCreate_shouldSetDefaultValues() {
    Section section = new Section();
    section.onCreate();
    assertNotNull(section.getSectionCreatedAt());
    assertNotNull(section.getSectionUpdatedAt());
  }

  @Test
  void onUpdate_shouldSetDefaultValues() {
    Section section = new Section();
    section.onUpdate();
    assertNotNull(section.getSectionUpdatedAt());
  }
}
