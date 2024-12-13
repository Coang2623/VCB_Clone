package com.vcb.backend.service;

import com.vcb.backend.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
  PermissionDto createPermission(PermissionDto permissionDto);

  PermissionDto updatePermission(PermissionDto permissionDto);

  void deletePermission(String permissionName);

  PermissionDto getPermissionWithFullInfo(String permissionName);

  List<PermissionDto> getAllPermissionsWithFullInfo();
}
