package com.vcb.backend.service;

import com.vcb.backend.service.impl.PasswordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class PasswordServiceTest {

  @Autowired
  private PasswordServiceImpl passwordService;

  @Test
  void passwordEncoder() {
    // Given
    String password = "testPassword";

    // When
    String encodedPassword = passwordService.passwordEncoder(password);

    // Then
    log.info("Encoded password: {}", encodedPassword);
    assert encodedPassword != null;
  }
}
