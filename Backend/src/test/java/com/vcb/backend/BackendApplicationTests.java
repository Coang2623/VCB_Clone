package com.vcb.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BackendApplicationTests {

  @Test
  void contextLoads() {
    assertDoesNotThrow(() -> {
      BackendApplication.main(new String[]{});
    });
  }

}