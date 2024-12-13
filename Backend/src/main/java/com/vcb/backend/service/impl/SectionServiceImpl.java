package com.vcb.backend.service.impl;

import com.vcb.backend.dto.SectionDto;
import com.vcb.backend.entity.Section;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.mapper.SectionMapper;
import com.vcb.backend.repository.SectionRepository;
import com.vcb.backend.service.SectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SectionServiceImpl implements SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private SectionMapper sectionMapper;

  /**
   * Get all sections with full info
   * @return list of sectionDto
   */
  @Override
  public List<SectionDto> getAllSectionsFullInfo() {
    return sectionRepository.findAll().stream().map(sectionMapper::toSectionDtoFullInfo).toList();
  }

  /**
   * Get all sections with basic info
   * @return list of sectionDto
   */
  @Override
  public List<SectionDto> getAllSectionsBasicInfo() {
    return sectionRepository.findAll().stream().map(sectionMapper::toSectionDtoBasicInfo).toList();
  }

  /**
   * Get section by id with full info
   * @param sectionId - id of section
   * @return section with full info
   */
  @Override
  public SectionDto getSectionByIdFullInfo(Long sectionId) {
    Section section = sectionRepository.findById(sectionId)
      .orElseThrow(() -> new AppException(AppError.SECTION_NOT_EXIST));
    return sectionMapper.toSectionDtoFullInfo(section);
  }

  /**
   * Get section by id with basic info
   * @param sectionId - id of section
   * @return section with basic info
   */
  @Override
  public SectionDto getSectionByIdBasicInfo(Long sectionId) {
    Section section = sectionRepository.findById(sectionId)
      .orElseThrow(() -> new AppException(AppError.SECTION_NOT_EXIST));
    return sectionMapper.toSectionDtoBasicInfo(section);
  }

  /**
   * Get section by name with full info
   * @param sectionName - name of section
   * @return section with full info
   */
  @Override
  public SectionDto getSectionByName(String sectionName) {
    Section section = sectionRepository.findBySectionName(sectionName);
    if (section != null) {
      return sectionMapper.toSectionDtoFullInfo(section);
    }else {
      throw new AppException(AppError.SECTION_NOT_EXIST);
    }
  }

  /**
   * Create new section
   * @param sectionDto - section object with info need to create
   * @return section
   */
  @Override
  public SectionDto createSection(SectionDto sectionDto) {
    if (sectionRepository.existsBySectionName(sectionDto.getSectionName())) {
      throw new AppException(AppError.SECTION_NAME_EXISTED);
    }

    Section section = new Section();
    sectionMapper.toCreateSection(section, sectionDto);

    return sectionMapper.toSectionDtoBasicInfo(sectionRepository.save(section));
  }

  /**
   * Update section
   * @param sectionDto - section object with info need to update
   * @return section
   */
  @Override
  public SectionDto updateSection(SectionDto sectionDto) {
    if (sectionDto.getSectionName() != null && sectionRepository.existsBySectionName(sectionDto.getSectionName())) {
      throw new AppException(AppError.SECTION_NAME_EXISTED);
    }

    Section section = sectionRepository.findById(sectionDto.getSectionId())
      .orElseThrow(() -> new AppException(AppError.SECTION_NOT_EXIST));

    sectionMapper.toUpdateSection(section, sectionDto);
    return sectionMapper.toSectionDtoBasicInfo(sectionRepository.save(section));
  }

  /**
   * Delete section by id
   * @param sectionId - id of section
   */
  @Override
  public void deleteSection(Long sectionId) {
    sectionRepository.deleteById(sectionId);
  }
}
