package com.vcb.backend.mapper;

import com.vcb.backend.dto.SectionDto;
import com.vcb.backend.entity.Section;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SectionMapper {
  Section toSection(SectionDto sectionDto);

  @Mapping(target = "sectionId", ignore = true)
  void toCreateSection(@MappingTarget Section section, SectionDto sectionDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdateSection(@MappingTarget Section section, SectionDto sectionDto);

  SectionDto toSectionDtoFullInfo(Section section);

  @Mapping(target = "sectionCreatedBy", ignore = true)
  @Mapping(target = "sectionCreatedAt", ignore = true)
  @Mapping(target = "sectionUpdatedBy", ignore = true)
  @Mapping(target = "sectionUpdatedAt", ignore = true)
  SectionDto toSectionDtoBasicInfo(Section section);
}
