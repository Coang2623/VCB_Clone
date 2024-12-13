package com.vcb.backend.controller;

import com.nimbusds.jose.JOSEException;
import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.AuthenticationDTO.AuthenticationRequest;
import com.vcb.backend.dto.AuthenticationDTO.AuthenticationResponse;
import com.vcb.backend.dto.AuthenticationDTO.LogoutRequest;
import com.vcb.backend.dto.AuthenticationDTO.RefreshRequest;
import com.vcb.backend.dto.IntrospectDTO.IntrospectRequest;
import com.vcb.backend.dto.IntrospectDTO.IntrospectResponse;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.AuthenticationService;
import lombok.AccessLevel;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Đăng nhập thành công");
        var authenticated = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticated)
                .build();
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var Result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(Result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
      log.warn("Đăng xuat");
        authenticationService.logout(request);
        log.warn("Đăng xuat thanh cong");
        return ApiResponse.<Void>builder()
                .code(AppError.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
