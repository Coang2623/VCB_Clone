package com.vcb.backend.service;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.entity.Permission;
import com.vcb.backend.entity.Role;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.PermissionMapper;
import com.vcb.backend.repository.RoleRepository;
import com.vcb.backend.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
class RoleServiceTest {
  @Autowired
  private RoleServiceImpl roleService;

  @Autowired
  private PermissionMapper permissionMapper;

  @MockBean
  private RoleRepository roleRepository;

  @MockBean AuthenticationService authenticationService;

  private RoleDto roleDtoReq;
  private RoleDto roleDtoResFullInfo;
  private RoleDto roleDtoResBasicInfo;
  private Role role;
  private Permission permission;


  @BeforeEach
  public void initData() {
    permission = Permission.builder()
      .permissionName("DELETE_USER")
      .permissionDescription("Delete user")
      .permissionCreatedAt(LocalDateTime.now())
      .permissionCreatedBy("1")
      .permissionUpdatedAt(LocalDateTime.now())
      .permissionUpdatedBy("1")
      .build();


    roleDtoReq = RoleDto.builder()
      .roleName("ADMIN")
      .roleDescription("Admin")
      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoBasicInfo(permission))))
      .build();

    roleDtoResFullInfo = RoleDto.builder()
      .roleName("ADMIN")
      .roleDescription("Admin")
      .roleCreatedAt(LocalDateTime.now())
      .roleCreatedBy("1")
      .roleUpdatedAt(LocalDateTime.now())
      .roleUpdatedBy("1")
      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoFullInfo(permission))))
      .build();

    roleDtoResBasicInfo = RoleDto.builder()
      .roleName("ADMIN")
      .roleDescription("Admin")
      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoBasicInfo(permission))))
      .build();

    role = Role.builder()
      .roleName("ADMIN")
      .roleDescription("Admin")
      .permissions(new HashSet<>(List.of(permission)))
      .roleCreatedAt(LocalDateTime.now())
      .roleUpdatedAt(LocalDateTime.now())
      .roleCreatedBy("1")
      .roleUpdatedBy("1")
      .build();
  }

  @Test
  void createRole_validRequest_success() {
    //Given
    when(roleRepository.existsByRoleName(roleDtoReq.getRoleName())).thenReturn(false);
    when(roleRepository.save(any())).thenReturn(role);
    when(authenticationService.getUsernameFromToken()).thenReturn("admin");

    //When
    var res = roleService.createRole(roleDtoReq);

    //Then
    Assertions.assertEquals(roleDtoResBasicInfo, res);
  }

  @Test
  void createRole_roleNameExists_fail() {
    //Given
    when(roleRepository.existsByRoleName(roleDtoReq.getRoleName())).thenReturn(true);
    when(authenticationService.getUsernameFromToken()).thenReturn("admin");

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> roleService.createRole(roleDtoReq));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.ROLE_NAME_EXISTED.getCode());
  }

  @Test
  void getAllRolesFullInfo_success() {
    //Given
    when(roleRepository.findAll()).thenReturn(List.of(role));

    log.info("Role: {}", role.getRoleCreatedAt());

    //When
    var res = roleService.getAllRolesFullInfo();

    //Then
    Assertions.assertEquals(List.of(roleDtoResFullInfo), res);
  }

  @Test
  void getAllRolesBasicInfo_success() {
    //Given
    when(roleRepository.findAll()).thenReturn(List.of(role));

    //When
    var res = roleService.getAllRolesBasicInfo();

    //Then
    Assertions.assertEquals(List.of(roleDtoResBasicInfo), res);
  }

  @Test
  void updateRole_validRequest_success() {
    //Given
    when(roleRepository.existsByRoleName(roleDtoReq.getRoleName())).thenReturn(true);
    when(roleRepository.save(any())).thenReturn(role);

    //When
    var res = roleService.updateRole(roleDtoReq);

    //Then
    Assertions.assertEquals(roleDtoResBasicInfo, res);
  }

  @Test
  void updateRole_roleNameNotExists_fail() {
    //Given
    when(roleRepository.existsByRoleName(roleDtoReq.getRoleName())).thenReturn(false);

    //When
    var exception = Assertions.assertThrows(AppException.class, () -> roleService.updateRole(roleDtoReq));

    //Then
    Assertions.assertEquals(exception.getAppError().getCode(), AppError.ROLE_NOT_EXIST.getCode());
  }

  @Test
  void deleteRole_success() {
    //Given

    //When
    roleService.deleteRole(roleDtoReq.getRoleName());

    //Then
    verify(roleRepository, times(1)).deleteById(roleDtoReq.getRoleName());
  }
}
