package com.vcb.backend.service.impl;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.entity.Role;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.PermissionMapper;
import com.vcb.backend.mapper.RoleMapper;
import com.vcb.backend.repository.PermissionRepository;
import com.vcb.backend.repository.RoleRepository;
import com.vcb.backend.service.AuthenticationService;
import com.vcb.backend.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleRepository roleRepository;
  @Autowired
  RoleMapper roleMapper;
  @Autowired
  PermissionMapper permissionMapper;
  @Autowired
  AuthenticationService authenticationService;

  /**
   * Get all roles with full info
   * @return list of roles
   */
  @Override
  public List<RoleDto> getAllRolesFullInfo() {
    return roleMapper.toRoleDtoFullInfoSet(new HashSet<>(roleRepository.findAll())).stream().toList();
  }

  /**
   * Get all roles with basic info
   * @return list of roles
   */
  @Override
  public List<RoleDto> getAllRolesBasicInfo() {
    return roleMapper.toRoleDtoBasicInfoSet(new HashSet<>(roleRepository.findAll())).stream().toList();
  }

  /**
   * Create new role
   * @param roleDto - role object with info need to create
   * @return role
   */
  @Override
  public RoleDto createRole(RoleDto roleDto) {
    if(roleRepository.existsByRoleName(roleDto.getRoleName())){
      throw new AppException(AppError.ROLE_NAME_EXISTED);
    }

    Role role = new Role();
    roleMapper.toCreateRole(role, roleDto);

    role.setRoleCreatedBy(authenticationService.getUsernameFromToken());
    role.setRoleUpdatedBy(authenticationService.getUsernameFromToken());

    role = roleRepository.save(role);

    return roleMapper.toRoleDtoBasicInfo(role);
  }

  /**
   * Update role
   * @param roleDto - role object with info need to update
   * @return role
   */
  @Override
  public RoleDto updateRole(RoleDto roleDto) {
    if(!roleRepository.existsByRoleName(roleDto.getRoleName())){
      throw new AppException(AppError.ROLE_NOT_EXIST);
    }

    Role role = new Role();
    roleMapper.toUpdateRole(role, roleDto);

    role.setRoleUpdatedBy(authenticationService.getUsernameFromToken());

    role = roleRepository.save(role);

    return roleMapper.toRoleDtoBasicInfo(role);
  }

  /**
   * Delete role
   * @param roleName - role name that you want to delete
   */
  @Override
  public void deleteRole(String roleName) {
    roleRepository.deleteById(roleName);
  }
}
