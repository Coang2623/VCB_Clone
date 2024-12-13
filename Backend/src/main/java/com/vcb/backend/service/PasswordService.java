package com.vcb.backend.service;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
  public String passwordEncoder(String password) throws Exception;
}
