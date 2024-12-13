package com.vcb.backend.dto;

import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionDto {
  @NotBlank(message = "SECTION_ID_REQUIRED", groups = {UpdateGroup.class})
  Long sectionId;
  @NotBlank(message = "SECTION_NAME_REQUIRED", groups = {CreateGroup.class})
  String sectionName;

  String sectionDescription;
  LocalDateTime sectionCreatedAt;
  String sectionCreatedBy;
  LocalDateTime sectionUpdatedAt;
  String sectionUpdatedBy;
}
