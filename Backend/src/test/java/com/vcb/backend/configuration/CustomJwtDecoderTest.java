package com.vcb.backend.configuration;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.vcb.backend.dto.IntrospectDTO.IntrospectRequest;
import com.vcb.backend.dto.IntrospectDTO.IntrospectResponse;
import com.vcb.backend.entity.User;
import com.vcb.backend.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.BaseStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class CustomJwtDecoderTest {

  @MockBean
  private AuthenticationService authenticationService;

  @Autowired
  private CustomJwtDecoder customJwtDecoder;

  @MockBean
  private NimbusJwtDecoder nimbusjwtDecoder;

  @Value("${jwt.signerKey}")
  private String signerKey;

  private String validToken;

  private final String invalidToken = "invalidToken";

  public String generateToken(User user) {
    JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .subject(user.getUserName())
      .issuer("vcb.com")
      .issueTime(new Date(System.currentTimeMillis()))
      .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
      .jwtID(UUID.randomUUID().toString())
      .build();

    Payload payload = new Payload(claimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(jwsHeader, payload);
    try {
      log.info("Signer key: {}", signerKey);
      jwsObject.sign(new MACSigner(signerKey));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() {
    //MockitoAnnotations.openMocks(this);
    validToken = generateToken(User.builder().userName("username").build());
  }

  @Test
  void decode_ValidToken_ShouldReturnJwt() throws ParseException, JOSEException {
    // Mock response for valid token
    IntrospectResponse mockResponse = IntrospectResponse.builder()
      .valid(true)
      .build();
    when(authenticationService.introspect(any(IntrospectRequest.class))).thenReturn(mockResponse);

    // Decode the token
    customJwtDecoder.setSignerKey(signerKey);
    customJwtDecoder.setNimbusjwtDecoder(null);
    Jwt jwt = customJwtDecoder.decode(validToken);

    assertNotNull(jwt);
    verify(authenticationService, times(1)).introspect(any(IntrospectRequest.class));
  }

  @Test
  void decode_InvalidToken_ShouldThrowJwtException() throws ParseException, JOSEException {
    // Mock response for invalid token
    IntrospectResponse mockResponse = IntrospectResponse.builder()
      .valid(false)
      .build();
    when(authenticationService.introspect(any(IntrospectRequest.class))).thenReturn(mockResponse);

    JwtException exception = assertThrows(JwtException.class, () -> customJwtDecoder.decode(invalidToken));

    assertEquals("Invalid token", exception.getMessage());
    verify(authenticationService, times(1)).introspect(any(IntrospectRequest.class));
  }

  @Test
  void decode_AuthenticationServiceThrowsParseException_ShouldThrowJwtException() throws ParseException, JOSEException {
    // Mock exception when calling authenticationService.introspect
    when(authenticationService.introspect(any(IntrospectRequest.class)))
      .thenThrow(new ParseException("Parsing error", 0));

    JwtException exception = assertThrows(JwtException.class, () -> customJwtDecoder.decode(validToken));

    assertEquals("Cannot decode token", exception.getMessage());
    verify(authenticationService, times(1)).introspect(any(IntrospectRequest.class));
  }

  @Test
  void decode_AuthenticationServiceThrowsJOSEException_ShouldThrowJwtException() throws ParseException, JOSEException {
    // Mock exception when calling authenticationService.introspect
    when(authenticationService.introspect(any(IntrospectRequest.class)))
      .thenThrow(new JOSEException("Decoding error"));

    JwtException exception = assertThrows(JwtException.class, () -> customJwtDecoder.decode(validToken));

    assertEquals("Cannot decode token", exception.getMessage());
    verify(authenticationService, times(1)).introspect(any(IntrospectRequest.class));
  }

  @Test
  void decode_NullSignerKey_ShouldThrowJwtException() {
    // Set signerKey to null
    customJwtDecoder.setSignerKey("");

    Exception exception = assertThrows(Exception.class, () -> customJwtDecoder.decode(validToken));

    assertEquals(NullPointerException.class, exception.getClass());
  }

  @Test
  void decode_ExistingNimbusJwtDecoder_ShouldNotCreateNew() throws ParseException, JOSEException {
    //Given
    NimbusJwtDecoder nimbusjwtDecoder = mock(NimbusJwtDecoder.class);
    when(authenticationService.introspect(any(IntrospectRequest.class))).thenReturn(IntrospectResponse.builder().valid(true).build());
    customJwtDecoder.setNimbusjwtDecoder(nimbusjwtDecoder);
    customJwtDecoder.setSignerKey(signerKey);

    //When
    customJwtDecoder.decode(validToken);

    //Then
    verify(nimbusjwtDecoder, times(1)).decode(validToken);
  }
}
