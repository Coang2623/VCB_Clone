package com.vcb.backend.service;

import com.vcb.backend.dto.SectionDto;

import java.util.List;

public interface SectionService {
  public List<SectionDto> getAllSectionsFullInfo();

  public List<SectionDto> getAllSectionsBasicInfo();

  public SectionDto getSectionByIdFullInfo(Long sectionId);

  public SectionDto getSectionByIdBasicInfo(Long sectionId);

  public SectionDto getSectionByName(String sectionName);

  public SectionDto createSection(SectionDto sectionDto);

  public SectionDto updateSection(SectionDto sectionDto);

  public void deleteSection(Long sectionId);

}
