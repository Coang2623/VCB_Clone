package com.vcb.backend.service.impl;

import com.vcb.backend.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@Service
public class PasswordServiceImpl implements PasswordService {

  // Random salt for hashing
  @Value("${app.password-salt}")
  private String salt;

  // Hash the password using SHA-512
  @Override
  public String passwordEncoder(String password) {
    try{

      //Configure the hashing algorithm
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt.getBytes(StandardCharsets.UTF_8));

      // Hash the password
      byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

      // Convert the hashed bytes to a hexadecimal string
      StringBuilder hexString = new StringBuilder(2 * hashedBytes.length);
      for (byte b : hashedBytes) {
        hexString.append(String.format("%02x", b));
      }

      return hexString.toString();
    }catch (NoSuchAlgorithmException e){
      log.error("Error hashing password", e);
      return null;
    }
  }
}
