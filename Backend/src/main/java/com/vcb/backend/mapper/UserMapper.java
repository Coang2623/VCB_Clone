package com.vcb.backend.mapper;

import com.vcb.backend.dto.userDto.UserDto;
import com.vcb.backend.dto.userDto.UserFullResponse;
import com.vcb.backend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

  @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleDtoBasicInfoSet")
  @Mapping(target = "userPassword", ignore = true)
  UserDto toUserResponseFullInfo(User user);

  @Mapping(target = "userPassword", ignore = true)
  @Mapping(target = "userUpdatedAt", ignore = true)
  @Mapping(target = "userUpdatedBy", ignore = true)
  @Mapping(target = "userCreatedBy", ignore = true)
  @Mapping(target = "userCreatedAt", ignore = true)
  @Mapping(target = "roles", source = "roles", qualifiedByName = "toRoleDtoBasicInfoSet")
  UserDto toUserResponseBasicInfo(User user);

  @Mapping(target = "userPassword", ignore = true)
  @Mapping(target = "userId", ignore = true)
  void toUserCreate( @MappingTarget User user, UserDto userDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "userPassword", ignore = true)
  void toUserUpdate( @MappingTarget User user, UserDto userDTO);
}
