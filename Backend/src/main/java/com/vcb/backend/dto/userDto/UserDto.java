package com.vcb.backend.dto.userDto;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.validator.dobValidator.DobConstraint;
import com.vcb.backend.validator.enumValidator.EnumConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
  @NotNull(message = "USER_ID_REQUIRED", groups = {UpdateGroup.class})
  Long userId;

  @NotBlank(message = "USERNAME_REQUIRED", groups = {CreateGroup.class})
  @Size(min = 5, message = "USERNAME_INVALID", groups = {CreateGroup.class, UpdateGroup.class})
  String userName;

  @NotBlank(message = "PASSWORD_REQUIRED", groups = {CreateGroup.class})
  @Size(min = 8, message = "PASSWORD_INVALID", groups = {CreateGroup.class, UpdateGroup.class})
  String userPassword;

  @NotBlank(message = "FIRST_NAME_REQUIRED", groups = {CreateGroup.class})
  String userFirstName;

  @NotBlank(message = "LAST_NAME_REQUIRED", groups = {CreateGroup.class})
  String userLastName;

  @NotBlank(message = "EMAIL_REQUIRED", groups = {CreateGroup.class})
  @Email(message = "EMAIL_INVALID", groups = {CreateGroup.class, UpdateGroup.class})
  String userEmail;

  @NotNull(message = "DOB_REQUIRED", groups = {CreateGroup.class})
  @DobConstraint(min = 18, message = "DOB_INVALID", groups = {CreateGroup.class, UpdateGroup.class})
  LocalDate userDateOfBirth;

  UserStatusEnum userStatus;

  Set<RoleDto> roles;

  LocalDateTime userCreatedAt;

  String userCreatedBy;

  LocalDateTime userUpdatedAt;

  String userUpdatedBy;
}
