package com.vcb.backend.service;

import com.vcb.backend.dto.userDto.UserDto;
import com.vcb.backend.dto.userDto.UserFullResponse;
import com.vcb.backend.entity.User;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.UserMapper;
import com.vcb.backend.repository.UserRepository;
import com.vcb.backend.service.impl.PasswordServiceImpl;
import com.vcb.backend.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
class UserServiceTest {

  @Autowired
  private UserServiceImpl userService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private UserMapper userMapper;

  @MockBean
  private PasswordServiceImpl passwordService;

  @MockBean
  private AuthenticationService authenticationService;


  private UserDto request;
  private User user;
  private UserDto expectedResponse;
  private LocalDate dateOfBirth;

  @BeforeEach
  void initData() {

    //Example of Valid request
    dateOfBirth = LocalDate.of(1999, 1, 1);
    request = UserDto.builder()
      .userFirstName("Nguyen")
      .userLastName("Phuoc")
      .userName("nguyenphuoc")
      .userPassword("12345678")
      .userEmail("BkxkZ@example.com")
      .userDateOfBirth(dateOfBirth)
      .build();

    //Expected response for example request
    expectedResponse = UserDto.builder()
      .userId(1L)
      .userFirstName("Nguyen")
      .userLastName("Phuoc")
      .userName("nguyenphuoc")
      .userEmail("BkxkZ@example.com")
      .userDateOfBirth(dateOfBirth)
      .userStatus(UserStatusEnum.DISABLED)
      .userCreatedBy("admin")
      .userCreatedAt(LocalDateTime.now())
      .userUpdatedAt(null)
      .build();

    //Example of Valid user
    user = User.builder()
      .userId(1L)
      .userFirstName("Nguyen")
      .userLastName("Phuoc")
      .userName("nguyenphuoc")
      .userEmail("BkxkZ@example.com")
      .userStatus(UserStatusEnum.DISABLED)
      .userDateOfBirth(dateOfBirth)
      .build();
  }

//  @Test
//  void createUser_validRequestWithoutUserStatus_success() {
//    //Given
//    request.setUserStatus(null);
//    when(userRepository.existsByUserName(anyString())).thenReturn(false);
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//    when(userRepository.save(any())).thenReturn(expectedResponse);
//    when(authenticationService.getUsernameFromToken()).thenReturn("admin");
//
//    //When
//    var res = userService.createUser(request);
//
//    //Then
//    Assertions.assertEquals(expectedResponse, res);
//  }
//
//  @Test
//  void createUser_validRequestWithUserStatus_success() {
//    //Given
//    request.setUserStatus(UserStatusEnum.DISABLED);
//    expectedResponse.setUserStatus(UserStatusEnum.DISABLED);
//    when(userRepository.existsByUserName(anyString())).thenReturn(false);
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//    when(userRepository.save(any())).thenReturn(user);
//    when(userMapper.toUserResponseBasicInfo(any())).thenReturn(expectedResponse);
//
//    //When
//    var res = userService.createUser(request);
//
//    //Then
//    Assertions.assertEquals(expectedResponse, res);
//  }
//
//  @Test
//  void createUser_userNameExists_fail() {
//when(userRepository.existsByUserName(anyString())).thenReturn(true);
//user.setUserStatus(UserStatusEnum.ACTIVE);
//    when(userRepository.existsByUserName(anyString())).thenReturn(true);
//
//    //When
//    var exception = Assertions.assertThrows(AppException.class, () -> userService.createUser(request));
//
//    //Then
//    Assertions.assertEquals(exception.getAppError().getCode(), AppError.USERNAME_EXISTED.getCode());
//  }
//
//  @Test
//  void createUser_encodesPassword(){
//    // Given
//    when(userRepository.existsByUserName(anyString())).thenReturn(false);
//    when(userRepository.save(any())).thenReturn(user);
//
//    // When
//    userService.createUser(request);
//
//    // Then
//    verify(passwordService, times(1)).passwordEncoder(request.getUserPassword());
//  }
//
//  @Test
//  void createUser_savesUserToRepository(){
//    // Given
//    when(userRepository.existsByUserName(anyString())).thenReturn(false);
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//
//    // When
//    userService.createUser(request);
//
//    // Then
//    verify(userRepository, times(1)).save(any(User.class));
//  }
//
//  @Test
//  void createUser_callsUserMapperToUserResponseBasicInfo(){
//    // Given
//    when(userRepository.existsByUserName(anyString())).thenReturn(false);
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//    when(userRepository.save(any())).thenReturn(user);
//
//    // When
//    userService.createUser(request);
//
//    // Then
//    verify(userMapper, times(1)).toUserResponseBasicInfo(any(User.class));
//  }
//
//  @Test
//  void updateUser_validRequest_success() {
//    //Given
//request.setUserId(1L);
//request.setUserStatus(UserStatusEnum.ACTIVE);
//
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//    when(userRepository.save(any())).thenReturn(user);
//    when(userMapper.toUserResponseBasicInfo(any())).thenReturn(expectedResponse);
//
//    //When
//    var res = userService.updateUser(request);
//
//    //Then
//    Assertions.assertEquals(expectedResponse, res);
//  }
//
//  @Test
//  void updateUser_userNotFound_fail() {
//    //Given
//    request.setUserId(1L);
//
//    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//    //When
//    var exception = Assertions.assertThrows(AppException.class, () -> userService.updateUser(request));
//
//    //Then
//    Assertions.assertEquals(exception.getAppError().getCode(), AppError.USER_NOT_EXIST.getCode());
//  }
//
//  @Test
//  void updateUser_encodesPassword() {
//    // Given
//    request.setUserId(1L);
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//
//    // When
//    userService.updateUser(request);
//
//    // Then
//    verify(passwordService, times(1)).passwordEncoder(request.getUserPassword());
//  }
//
//  @Test
//  void updateUser_savesUserToRepository(){
//    // Given
//    request.setUserId(1L);
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//
//    // When
//    userService.updateUser(request);
//
//    // Then
//    verify(userRepository, times(1)).save(any(User.class));
//  }
//
//  @Test
//  void updateUser_callsUserMapperToUserResponseBasicInfo() {
//    // Given
//    request.setUserId(1L);
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//    when(passwordService.passwordEncoder(anyString())).thenReturn("encodedPassword");
//    when(userRepository.save(any())).thenReturn(user);
//
//    // When
//    userService.updateUser(request);
//
//    // Then
//    verify(userMapper, times(1)).toUserResponseBasicInfo(any(User.class));
//  }
//
//  @Test
//  void deleteUser_validRequest_success() {
//    //Given
//request.setUserId(1L);
//user.setUserStatus(UserStatusEnum.ACTIVE);
//when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//
//    //When
//userService.deleteUser(request.getUserId());
//verify(userRepository, times(1)).save(any(User.class));
//
//    //Then
//    Assertions.assertEquals(user.getUserStatus(), UserStatusEnum.DELETED);
//  }
//
//  @Test
//  void getUserWithFullInfo_validRequest_success() {
//    //Given
//    Long userID = 1L;
//
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//    when(userMapper.toUserResponseFullInfo(user)).thenReturn(expectedResponse);
//
//    //When
//    var res = userService.getUserWithFullInfo(userID);
//    //Then
//    Assertions.assertEquals(expectedResponse, res);
//  }
//
//  @Test
//  void getUserWithFullInfo_userNotFound_fail() {
//    //Given
//    Long userID = 1L;
//
//    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//    //When
//    var exception = Assertions.assertThrows(AppException.class, () -> userService.getUserWithFullInfo(userID));
//
//    //Then
//    Assertions.assertEquals(exception.getAppError().getCode(), AppError.USER_NOT_EXIST.getCode());
//  }
//
//  @Test
//  void getUserWithFullInfo_callsUserMapperToUserResponseFullInfo() {
//    // Given
//    Long userID = 1L;
//    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//
//    // When
//    userService.getUserWithFullInfo(userID);
//
//    // Then
//    verify(userMapper, times(1)).toUserResponseFullInfo(any(User.class));
//  }
//
//  @Test
//  void getAllUsersWithFullInfo_success() {
//    //Given
//    when(userRepository.findAll()).thenReturn(List.of(user));
//    when(userMapper.toUserResponseFullInfo(user)).thenReturn(expectedResponse);
//
//    var res = userService.getAllUsersWithFullInfo();
//
//    //Then
//    Assertions.assertEquals(List.of(expectedResponse), res);
//  }
//
//  @Test
//  void getAllUsersWithFullInfo_callsUserMapperToUserResponseFullInfo() {
//    // Given
//    when(userRepository.findAll()).thenReturn(List.of(user));
//
//    // When
//    userService.getAllUsersWithFullInfo();
//
//    // Then
//    verify(userMapper, times(1)).toUserResponseFullInfo(any(User.class));
//  }
}
