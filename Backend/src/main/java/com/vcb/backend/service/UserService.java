package com.vcb.backend.service;

import com.vcb.backend.dto.userDto.UserDto;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto userDTO);

  UserDto updateUser(UserDto userDTO);

  void deleteUser(Long userID);

  UserDto getUserWithFullInfo(Long userID);

  List<UserDto> getAllUsersWithFullInfo();

  List<UserDto> getAllUsersWithBasicInfo();

  List<String> getAllUserStatus();
}
