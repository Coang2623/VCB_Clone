package com.vcb.backend.mapper;

import com.vcb.backend.dto.PermissionDto;
import com.vcb.backend.entity.Permission;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  Permission toPermission(PermissionDto permissionDto);

  @Named("toPermissionDtoFullInfo")
  PermissionDto toPermissionDtoFullInfo(Permission permission);

  @Named("toPermissionDtoBasicInfo")
  @Mapping(target = "permissionUpdatedAt", ignore = true)
  @Mapping(target = "permissionUpdatedBy", ignore = true)
  @Mapping(target = "permissionCreatedBy", ignore = true)
  @Mapping(target = "permissionCreatedAt", ignore = true)
  PermissionDto toPermissionDtoBasicInfo(Permission permission);

  void toCreatePermission(@MappingTarget Permission permission, PermissionDto permissionDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdatePermission(@MappingTarget Permission permission, PermissionDto permissionDto);

  @Named("toPermissionDtoBasicInfoSet")
  @IterableMapping(qualifiedByName = "toPermissionDtoBasicInfo")
  Set<PermissionDto> toPermissionDtoBasicInfoSet(Set<Permission> permissions);
}
