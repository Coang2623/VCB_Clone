package com.vcb.backend.dto.userDto;

import com.vcb.backend.dto.RoleDto;
import com.vcb.backend.enums.UserStatusEnum;
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
public class UserFullResponse {
  Long userId;
  String userName;
  String userFirstName;
  String userLastName;
  String userEmail;
  LocalDate userDateOfBirth;
  UserStatusEnum userStatus;
  Set<RoleDto> roles;
  LocalDateTime userCreatedAt;
  String userCreatedBy;
  LocalDateTime userUpdatedAt;
  String userUpdatedBy;
}
