package com.vcb.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.entity.Permission;
import com.vcb.backend.entity.Role;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.mapper.PermissionMapper;
import com.vcb.backend.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class RoleControllerTest {
//  @Autowired
//  private MockMvc mockMvc;
//
//  @Autowired
//  private PermissionMapper permissionMapper;
//
//  @MockBean
//  private RoleServiceImpl roleService;
//
//  private RoleDto roleDtoReq;
//  private RoleDto roleDtoResFullInfo;
//  private RoleDto roleDtoResBasicInfo;
//  private Role role;
//  private Permission permission;
//
//  @BeforeEach
//  public void initData() {
//    permission = Permission.builder()
//      .permissionName("DELETE_USER")
//      .permissionDescription("Delete user")
//      .permissionCreatedAt(LocalDateTime.now())
//      .permissionUpdatedAt(LocalDateTime.now())
//      .build();
//
//
//    roleDtoReq = RoleDto.builder()
//      .roleName("ADMIN")
//      .roleDescription("Admin")
//      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoBasicInfo(permission))))
//      .build();
//
//    roleDtoResFullInfo = RoleDto.builder()
//      .roleName("ADMIN")
//      .roleDescription("Admin")
//      .roleCreatedAt(LocalDateTime.now())
//      .roleCreatedBy("1")
//      .roleUpdatedAt(LocalDateTime.now())
//      .roleUpdatedBy("1")
//      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoFullInfo(permission))))
//      .build();
//
//    roleDtoResBasicInfo = RoleDto.builder()
//      .roleName("ADMIN")
//      .roleDescription("Admin")
//      .permissions(new HashSet<>(List.of(permissionMapper.toPermissionDtoBasicInfo(permission))))
//      .build();
//
//    role = Role.builder()
//      .roleName("ADMIN")
//      .roleDescription("Admin")
//      .permissions(new HashSet<>(List.of(permission)))
//      .roleCreatedAt(LocalDateTime.now())
//      .roleUpdatedAt(LocalDateTime.now())
//      .roleCreatedBy("1")
//      .roleUpdatedBy("1")
//      .build();
//  }
//
//  @Test
//  public void getAllRolesFullInfo_success() throws Exception {
//
//    //When
//    when(roleService.getAllRolesFullInfo()).thenReturn(List.of(roleDtoResFullInfo));
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/role/all")
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Get all roles successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleName").value(roleDtoResFullInfo.getRoleName()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleDescription").value(roleDtoResFullInfo.getRoleDescription()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleCreatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleCreatedById").value(roleDtoResFullInfo.getRoleCreatedBy()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleUpdatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].roleUpdatedById").value(roleDtoResFullInfo.getRoleUpdatedBy()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionName").value(roleDtoResFullInfo.getPermissions().iterator().next().getPermissionName()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionDescription").value(roleDtoResFullInfo.getPermissions().iterator().next().getPermissionDescription()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionCreatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionCreatedById").value(roleDtoResFullInfo.getPermissions().iterator().next().getPermissionCreatedBy()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionUpdatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result[0].permissions[0].permissionUpdatedById").value(roleDtoResFullInfo.getPermissions().iterator().next().getPermissionUpdatedBy()))
//    ;
//
//  }
//
//  @Test
//  public void getAllRolesBasicInfo_success() throws Exception {
//
//    //When
//    when(roleService.getAllRolesBasicInfo()).thenReturn(List.of(roleDtoResBasicInfo));
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Get all roles successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleName").value("ADMIN"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleDescription").value("Admin"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleCreatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleCreatedById").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleUpdatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].roleUpdatedById").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionName").value("DELETE_USER"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionDescription").value("Delete user"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionCreatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionCreatedById").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionUpdatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].permissions[0].permissionUpdatedById").isEmpty())
//    ;
//  }
//
//  @Test
//  public void createRole_validRequest_success() throws Exception {
//    //Create object mapper
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //Register java time module
//    objectMapper.registerModule(new JavaTimeModule());
//
//    //When
//    when(roleService.createRole(roleDtoReq)).thenReturn(roleDtoResBasicInfo);
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(objectMapper.writeValueAsString(roleDtoReq)))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create role successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleName").value(roleDtoResBasicInfo.getRoleName()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleDescription").value(roleDtoResBasicInfo.getRoleDescription()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedById").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedById").isEmpty())
//    ;
//  }
//
//  @Test
//  public void createRole_nullPermissions_success() throws Exception {
//
//    roleDtoReq.setPermissions(null);
//    roleDtoResBasicInfo.setPermissions(null);
//    //Create object mapper
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //Register java time module
//    objectMapper.registerModule(new JavaTimeModule());
//
//    //When
//    when(roleService.createRole(roleDtoReq)).thenReturn(roleDtoResBasicInfo);
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(objectMapper.writeValueAsString(roleDtoReq)))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create role successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleName").value(roleDtoResBasicInfo.getRoleName()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleDescription").value(roleDtoResBasicInfo.getRoleDescription()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.permissions").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedById").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedAt").isEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedById").isEmpty())
//    ;
//  }
//
//  @Test
//  public void createRole_invalidRoleName_fail() throws Exception {
//
//    roleDtoReq.setRoleName(null);
//    //Create object mapper
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //Register java time module
//    objectMapper.registerModule(new JavaTimeModule());
//
//    //When
//    when(roleService.createRole(roleDtoReq)).thenReturn(roleDtoResBasicInfo);
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(objectMapper.writeValueAsString(roleDtoReq)))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.ROLE_NAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.ROLE_NAME_REQUIRED.getMessage()))
//    ;
//  }
//
//  @Test
//  public void updateRole_validRequest_success() throws Exception {
//    //Create object mapper
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //Register java time module
//    objectMapper.registerModule(new JavaTimeModule());
//
//    //When
//    when(roleService.updateRole(roleDtoReq)).thenReturn(roleDtoResFullInfo);
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .put("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(objectMapper.writeValueAsString(roleDtoReq)))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Update role successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleName").value(roleDtoResFullInfo.getRoleName()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleDescription").value(roleDtoResFullInfo.getRoleDescription()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleCreatedById").value(roleDtoResFullInfo.getRoleCreatedBy()))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedAt").isNotEmpty())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.roleUpdatedById").value(roleDtoResFullInfo.getRoleUpdatedBy()))
//    ;
//  }
//
//  @Test
//  public void updateRole_invalidRoleName_fail() throws Exception {
//
//    roleDtoReq.setRoleName(null);
//    //Create object mapper
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    //Register java time module
//    objectMapper.registerModule(new JavaTimeModule());
//
//    //When
//    when(roleService.updateRole(roleDtoReq)).thenReturn(roleDtoResFullInfo);
//
//    mockMvc.perform(MockMvcRequestBuilders
//        .put("/role")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(objectMapper.writeValueAsString(roleDtoReq)))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.ROLE_NAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.ROLE_NAME_REQUIRED.getMessage()))
//    ;
//  }
//
//  @Test
//  public void deleteRole_validRequest_success() throws Exception {
//    //When
//    doNothing().when(roleService).deleteRole(role.getRoleName());
//    mockMvc.perform(MockMvcRequestBuilders
//        .delete("/role/{roleName}", role.getRoleName())
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Delete role successfully"))
//    ;
//  }
}
