package com.vcb.backend.dto;

import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {
  @NotBlank(message = "ROLE_NAME_REQUIRED", groups = {CreateGroup.class, UpdateGroup.class})
  String roleName;

  String roleDescription;

  Set<PermissionDto> permissions;

  LocalDateTime roleCreatedAt;
  String roleCreatedBy;

  LocalDateTime roleUpdatedAt;
  String roleUpdatedBy;
}
