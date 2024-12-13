package com.vcb.backend.controller;

import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private RoleServiceImpl roleService;

  @GetMapping("/all")
  public ApiResponse<List<RoleDto>> getAllRolesWithFullInfo() {
    return ApiResponse.<List<RoleDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all roles successfully")
      .result(roleService.getAllRolesFullInfo())
      .build();
  }

  @GetMapping("/all-basic")
  public ApiResponse<List<RoleDto>> getAllRolesBasicInfo() {
    return ApiResponse.<List<RoleDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all roles successfully")
      .result(roleService.getAllRolesBasicInfo())
      .build();
  }

  @PostMapping
  public ApiResponse<RoleDto> createRole(@RequestBody @Validated(CreateGroup.class) RoleDto roleDto) {
    return ApiResponse.<RoleDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Create role successfully")
      .result(roleService.createRole(roleDto))
      .build();
  }

  @DeleteMapping("/{roleName}")
  public ApiResponse<Void> deleteRole(@PathVariable("roleName") String roleName) {
    roleService.deleteRole(roleName);
    return ApiResponse.<Void>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Delete role successfully")
      .build();
  }

  @PutMapping
  public ApiResponse<RoleDto> updateRole(@RequestBody @Validated(CreateGroup.class) RoleDto roleDto) {
    return ApiResponse.<RoleDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Update role successfully")
      .result(roleService.updateRole(roleDto))
      .build();
  }
}
