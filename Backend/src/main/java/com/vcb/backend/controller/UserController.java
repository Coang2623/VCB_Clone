package com.vcb.backend.controller;

import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.userDto.UserDto;
import com.vcb.backend.dto.userDto.UserFullResponse;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserServiceImpl userService;

  @PostMapping
  public ApiResponse<UserDto> createUser(@RequestBody @Validated(CreateGroup.class) UserDto userDTO) {
    return ApiResponse.<UserDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("User created successfully")
      .result(userService.createUser(userDTO))
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @GetMapping("/all")
  public ApiResponse<List<UserDto>> getAllUsers() {
    return ApiResponse.<List<UserDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all users successfully")
      .result(userService.getAllUsersWithFullInfo())
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @GetMapping("/all-basic")
  public ApiResponse<List<UserDto>> getAllUsersBasicInfo() {
    return ApiResponse.<List<UserDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all users successfully")
      .result(userService.getAllUsersWithBasicInfo())
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @GetMapping
  public ApiResponse<UserDto> getUserWithFullInfo(@RequestParam("userID") Long userID) {
    return ApiResponse.<UserDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get user " + userID + " with full info successfully")
      .result(userService.getUserWithFullInfo(userID))
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @PutMapping
  public ApiResponse<UserDto> updateUser(@RequestBody @Validated(UpdateGroup.class) UserDto userDTO) {
    return ApiResponse.<UserDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Update user successfully")
      .result(userService.updateUser(userDTO))
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @DeleteMapping
  public ApiResponse<Void> deleteUser(@RequestParam("userID") Long userID) {
    userService.deleteUser(userID);
    return ApiResponse.<Void>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Delete user successfully")
      .build();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER_MANAGER')")
  @GetMapping("/status")
  public ApiResponse<List<String>> getAllUserStatus() {
    return ApiResponse.<List<String>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all user status successfully")
      .result(userService.getAllUserStatus())
      .build();
  }
}
