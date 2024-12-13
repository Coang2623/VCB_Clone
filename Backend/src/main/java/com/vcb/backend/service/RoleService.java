package com.vcb.backend.service;

import com.vcb.backend.dto.RoleDto;

import java.util.List;

public interface RoleService {
  public List<RoleDto> getAllRolesFullInfo();

  public List<RoleDto> getAllRolesBasicInfo();

  public RoleDto createRole(RoleDto roleDto);

  public RoleDto updateRole(RoleDto roleDto);

  public void deleteRole(String RoleName);
}
