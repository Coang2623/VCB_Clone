package com.vcb.backend.service.impl;

import com.vcb.backend.dto.PermissionDto;
import com.vcb.backend.entity.Permission;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.PermissionMapper;
import com.vcb.backend.repository.PermissionRepository;
import com.vcb.backend.service.AuthenticationService;
import com.vcb.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  PermissionRepository permissionRepository;
  @Autowired
  PermissionMapper permissionMapper;
  @Autowired
  AuthenticationService authenticationService;

  /**
   * Method create new permission
   * @param permissionDto - permission object that contains permission information to create
   * @return permission object with basic information
   */
  @Override
  public PermissionDto createPermission(PermissionDto permissionDto) {
    if(permissionDto.getPermissionName() == null) {
      throw new AppException(AppError.PERMISSION_NAME_REQUIRED);
    }else if (permissionRepository.existsByPermissionName(permissionDto.getPermissionName())) {
      throw new AppException(AppError.PERMISSION_NAME_EXISTED);
    }

    Permission permission = new Permission();
    permissionMapper.toCreatePermission(permission, permissionDto);

    permission.setPermissionCreatedBy(authenticationService.getUsernameFromToken());
    permission.setPermissionUpdatedBy(authenticationService.getUsernameFromToken());

    return permissionMapper.toPermissionDtoBasicInfo(permissionRepository.save(permission));
  }

  /**
   * Method update permission
   * @param permissionDto - permission object that contains permission information to update
   * @return permission object with basic information
   */
  @Override
  public PermissionDto updatePermission(PermissionDto permissionDto) {
    Permission permission = permissionRepository.findById(permissionDto.getPermissionName()).orElseThrow(
      () -> new AppException(AppError.PERMISSION_NOT_EXIST)
    );

    permissionMapper.toUpdatePermission(permission, permissionDto);

    permission.setPermissionUpdatedBy(authenticationService.getUsernameFromToken());

    return permissionMapper.toPermissionDtoBasicInfo(permissionRepository.save(permission));
  }

  /**
   * Method delete permission
   * @param permissionName - permission name to delete
   */
  @Override
  public void deletePermission(String permissionName) {
    permissionRepository.deleteById(permissionName);
  }

  /**
   * Method get permission with full information
   * @param permissionName - permission name that you want to get
   * @return permission object with full information
   */
  @Override
  public PermissionDto getPermissionWithFullInfo(String permissionName) {
    Permission permission = permissionRepository.findById(permissionName).orElseThrow(
      () -> new AppException(AppError.PERMISSION_NOT_EXIST)
    );
    return permissionMapper.toPermissionDtoFullInfo(permission);
  }

  /**
   * Method get all permissions with full information
   * @return list of permission object with full information
   */
  @Override
  public List<PermissionDto> getAllPermissionsWithFullInfo() {
    return permissionRepository.findAll().stream().map(permissionMapper::toPermissionDtoFullInfo).toList();
  }
}
