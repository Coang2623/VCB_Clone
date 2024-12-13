package com.vcb.backend.dto;

import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionDto {
  @NotBlank(message = "PERMISSION_NAME_REQUIRED", groups = {CreateGroup.class, UpdateGroup.class})
  String permissionName;

  String permissionDescription;

  LocalDateTime permissionCreatedAt;
  String permissionCreatedBy;

  LocalDateTime permissionUpdatedAt;
  String permissionUpdatedBy;
}
