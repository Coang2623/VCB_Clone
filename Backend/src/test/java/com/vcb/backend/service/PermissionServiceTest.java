package com.vcb.backend.service;

import com.vcb.backend.dto.PermissionDto;
import com.vcb.backend.entity.Permission;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.repository.PermissionRepository;
import com.vcb.backend.service.impl.PermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
class PermissionServiceTest {
  @Autowired
  private PermissionServiceImpl permissionService;

  @MockBean
  private PermissionRepository permissionRepository;

  @MockBean
  private AuthenticationService authenticationService;

  PermissionDto permissionDtoReq;
  PermissionDto permissionDtoRes;
  Permission permission;


  @BeforeEach
  void initData() {
    permissionDtoReq = PermissionDto.builder()
      .permissionName("DELETE_USER")
      .permissionDescription("Delete user")
      .build();

    permissionDtoRes = PermissionDto.builder()
      .permissionName("DELETE_USER")
      .permissionDescription("Delete user")
      .build();

    permission = Permission.builder()
      .permissionName("DELETE_USER")
      .permissionDescription("Delete user")
      .build();
  }

  @Test
  void createPermission_validRequest_success() {
    //Given
    when(permissionRepository.existsByPermissionName(permissionDtoReq.getPermissionName())).thenReturn(false);
    when(permissionRepository.save(any())).thenReturn(permission);
    when(authenticationService.getUsernameFromToken()).thenReturn("admin");

    //When
    var res = permissionService.createPermission(permissionDtoReq);

    //Then
    Assertions.assertEquals(permissionDtoReq, res);
  }

  @Test
  void createPermission_permissionNameExists_fail() {
    //Given
    when(permissionRepository.existsByPermissionName(permissionDtoReq.getPermissionName())).thenReturn(true);

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> permissionService.createPermission(permissionDtoReq));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.PERMISSION_NAME_EXISTED.getCode());
  }

  @Test
  void createPermission_permissionNameRequire_fail() {
    //Given
    permissionDtoReq.setPermissionName(null);

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> permissionService.createPermission(permissionDtoReq));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.PERMISSION_NAME_REQUIRED.getCode());
  }

  @Test
  void getPermissionById_permissionIdExists_success() {
    //Given
    when(permissionRepository.findById(permissionDtoReq.getPermissionName())).thenReturn(Optional.of(permission));

    //When
    var res = permissionService.getPermissionWithFullInfo(permissionDtoReq.getPermissionName());

    //Then
    Assertions.assertEquals(permissionDtoRes, res);
  }

  @Test
  void getPermissionById_permissionIdNotExists_fail() {
    //Given
    var permissionName = permissionDtoReq.getPermissionName();
    when(permissionRepository.findById(permissionDtoReq.getPermissionName())).thenReturn(Optional.empty());

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> permissionService.getPermissionWithFullInfo(permissionName));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.PERMISSION_NOT_EXIST.getCode());
  }

  @Test
  void updatePermission_permissionIdExists_success() {
    //Given
    when(permissionRepository.findById(permissionDtoReq.getPermissionName())).thenReturn(Optional.of(permission));
    when(permissionRepository.save(any())).thenReturn(permission);

    //When
    var res = permissionService.updatePermission(permissionDtoReq);

    //Then
    Assertions.assertEquals(permissionDtoRes, res);
  }

  @Test
  void updatePermission_permissionIdNotExists_fail() {
    //Given
    when(permissionRepository.findById(permissionDtoReq.getPermissionName())).thenReturn(Optional.empty());

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> permissionService.updatePermission(permissionDtoReq));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.PERMISSION_NOT_EXIST.getCode());
  }

  @Test
  void getAllPermission_success() {
    //Given
    when(permissionRepository.findAll()).thenReturn(List.of(permission));

    //When
    var res = permissionService.getAllPermissionsWithFullInfo();

    //Then
    Assertions.assertEquals(List.of(permissionDtoRes), res);
  }

  @Test
  void deletePermission_permissionIdExists_success() {

    //When
    permissionService.deletePermission(anyString());

    //Then
    verify(permissionRepository, times(1)).deleteById(anyString());
  }
}
