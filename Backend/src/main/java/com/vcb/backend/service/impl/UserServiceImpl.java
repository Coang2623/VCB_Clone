package com.vcb.backend.service.impl;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.dto.userDto.UserDto;
import com.vcb.backend.dto.userDto.UserFullResponse;
import com.vcb.backend.entity.Role;
import com.vcb.backend.entity.User;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.RoleMapper;
import com.vcb.backend.mapper.UserMapper;
import com.vcb.backend.repository.RoleRepository;
import com.vcb.backend.repository.UserRepository;
import com.vcb.backend.service.AuthenticationService;
import com.vcb.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserMapper userMapper;

  @Autowired
  RoleMapper roleMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordServiceImpl passwordService;

  @Autowired
  private AuthenticationService authenticationService;


  @Override
  public UserDto createUser(UserDto userDTO) {
    //Check if username existed
    if(userRepository.existsByUserName(userDTO.getUserName())) {
      throw new AppException(AppError.USERNAME_EXISTED);
    }

    //Create new user
    User user = new User();

    //Map userDto to new user
    userMapper.toUserCreate(user, userDTO);

    //Encode password
    user.setUserPassword(passwordService.passwordEncoder(userDTO.getUserPassword()));

    //Set user status, default is disabled
    if(userDTO.getUserStatus() == null) {
      user.setUserStatus(UserStatusEnum.DISABLED);
    }else {
      user.setUserStatus(userDTO.getUserStatus());
    }

    //Set roles
    Set<Role> roles;

    if(userDTO.getRoles() != null) {
      roles = new HashSet<>(roleRepository.findAllById(userDTO.getRoles().stream().map(RoleDto::getRoleName).toList()));
      user.setRoles(roles);
    }

    //Set created by
    user.setUserCreatedBy(authenticationService.getUsernameFromToken());

    //Set updated by
    user.setUserUpdatedBy(authenticationService.getUsernameFromToken());

    //Map user to userFullResponse
    return userMapper.toUserResponseBasicInfo(userRepository.save(user));
  }

  @Override
  public UserDto updateUser(UserDto userDTO) {
    User user = userRepository.findById(userDTO.getUserId()).orElseThrow(
      () -> new AppException(AppError.USER_NOT_EXIST)
    );

    if(userRepository.existsByUserName(userDTO.getUserName()) && !user.getUserName().equals(userDTO.getUserName())) {
      throw new AppException(AppError.USERNAME_EXISTED);
    }

    userMapper.toUserUpdate(user, userDTO);

    if(userDTO.getUserPassword() != null) {
      user.setUserPassword(passwordService.passwordEncoder(userDTO.getUserPassword()));
    }

    if(userDTO.getRoles() != null) {
      Set<Role> roles = new HashSet<>(roleRepository.findAllById(userDTO.getRoles().stream().map(RoleDto::getRoleName).toList()));

      user.setRoles(roles);
    }

    user.setUserUpdatedBy(authenticationService.getUsernameFromToken());

    return userMapper.toUserResponseBasicInfo(userRepository.save(user));
  }

  @Override
  public void deleteUser(Long userID) {
    User user = userRepository.findById(userID).orElseThrow(
      () -> new AppException(AppError.USER_NOT_EXIST)
    );

    user.setUserStatus(UserStatusEnum.DELETED);

    user.setUserUpdatedBy(authenticationService.getUsernameFromToken());
    userRepository.save(user);
  }

  @Override
  public UserDto getUserWithFullInfo(Long userID) {
    User user = userRepository.findById(userID).orElseThrow(
      () -> new AppException(AppError.USER_NOT_EXIST)
    );
    return userMapper.toUserResponseFullInfo(user);
  }

  @Override
  public List<UserDto> getAllUsersWithFullInfo() {
    return userRepository.findUsersWithStatusNot(UserStatusEnum.DELETED).stream().map(userMapper::toUserResponseFullInfo).toList();
  }

  @Override
  public List<String> getAllUserStatus() {
    return Arrays.stream(UserStatusEnum.values())
        .map(UserStatusEnum::getUserStatus).toList();
  }

  public List<UserDto> getAllUsersWithBasicInfo() {
    List<User> users = userRepository.findUsersWithStatusNot(UserStatusEnum.DELETED);

    return users.stream().map(userMapper::toUserResponseBasicInfo).toList();
  }
}
