package com.vcb.backend.mapper;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.entity.Role;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {

  Role toRole(RoleDto roleDto);

  @Named("toRoleDtoFullInfo")
  RoleDto toRoleDtoFullInfo(Role role);

  @Named("toRoleDtoBasicInfo")
  @Mapping(target = "roleUpdatedAt", ignore = true)
  @Mapping(target = "roleUpdatedBy", ignore = true)
  @Mapping(target = "roleCreatedBy", ignore = true)
  @Mapping(target = "roleCreatedAt", ignore = true)
  @Mapping(target = "permissions", source = "permissions", qualifiedByName = "toPermissionDtoBasicInfoSet")
  RoleDto toRoleDtoBasicInfo(Role role);

  void toCreateRole(@MappingTarget Role role, RoleDto roleDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdateRole(@MappingTarget Role role, RoleDto roleDto);

  @Named("toRoleDtoFullInfoSet")
  @IterableMapping(qualifiedByName = "toRoleDtoFullInfo")
  Set<RoleDto> toRoleDtoFullInfoSet(Set<Role> roles);

  @Named("toRoleDtoBasicInfoSet")
  @IterableMapping(qualifiedByName = "toRoleDtoBasicInfo")
  Set<RoleDto> toRoleDtoBasicInfoSet(Set<Role> roles);
}
