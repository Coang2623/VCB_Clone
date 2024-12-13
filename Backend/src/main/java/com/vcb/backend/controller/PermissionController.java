package com.vcb.backend.controller;

import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.PermissionDto;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
public class PermissionController {

  @Autowired
  private PermissionServiceImpl permissionService;

  @GetMapping("/all")
  public ApiResponse<List<PermissionDto>> getAllPermission() {
    return ApiResponse.<List<PermissionDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all permission successfully")
      .result(permissionService.getAllPermissionsWithFullInfo())
      .build();
  }

  @GetMapping("/{permissionName}")
  public ApiResponse<PermissionDto> getPermissionWithFullInfo(@PathVariable("permissionName") String permissionName) {
    return ApiResponse.<PermissionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get permission successfully")
      .result(permissionService.getPermissionWithFullInfo(permissionName))
      .build();
  }

  @PostMapping
  public ApiResponse<PermissionDto> createPermission(@RequestBody @Validated(CreateGroup.class) PermissionDto permissionDto) {
    return ApiResponse.<PermissionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Create permission successfully")
      .result(permissionService.createPermission(permissionDto))
      .build();
  }

  @PutMapping
  public ApiResponse<PermissionDto> updatePermission(@RequestBody @Validated(UpdateGroup.class) PermissionDto permissionDto) {
    return ApiResponse.<PermissionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Update permission successfully")
      .result(permissionService.updatePermission(permissionDto))
      .build();
  }

  @DeleteMapping("/{permissionName}")
  public ApiResponse<Void> deletePermission(@PathVariable("permissionName") String permissionName) {
    permissionService.deletePermission(permissionName);
    return ApiResponse.<Void>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Delete permission successfully")
      .build();
  }
}
