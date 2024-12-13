package com.vcb.backend.controller;

import com.vcb.backend.dto.ApiResponse;
import com.vcb.backend.dto.SectionDto;
import com.vcb.backend.dto.validGroup.CreateGroup;
import com.vcb.backend.dto.validGroup.UpdateGroup;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.SectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {
  @Autowired
  SectionServiceImpl sectionService;

  @PostMapping("/create")
  public ApiResponse<SectionDto> createSection(@RequestBody @Validated(CreateGroup.class) SectionDto sectionDto) {
    return ApiResponse.<SectionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .result(sectionService.createSection(sectionDto))
      .message("Create section successfully")
      .build();
  }

  @PutMapping("/update")
  public ApiResponse<SectionDto> updateSection(@RequestBody @Validated(UpdateGroup.class) SectionDto sectionDto) {
    return ApiResponse.<SectionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .result(sectionService.updateSection(sectionDto))
      .message("Update section successfully")
      .build();
  }

  @GetMapping("/all")
  public ApiResponse<List<SectionDto>> getAllSectionsWithFullInfo() {
    return ApiResponse.<List<SectionDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all sections successfully")
      .result(sectionService.getAllSectionsFullInfo())
      .build();
  }

  @GetMapping("/all-basic")
  public ApiResponse<List<SectionDto>> getAllSectionsBasicInfo() {
    return ApiResponse.<List<SectionDto>>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get all sections successfully")
      .result(sectionService.getAllSectionsBasicInfo())
      .build();
  }

  @GetMapping("/{sectionId}")
  public ApiResponse<SectionDto> getSectionWithFullInfo(@PathVariable("sectionId") Long sectionId) {
    return ApiResponse.<SectionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get section successfully")
      .result(sectionService.getSectionByIdFullInfo(sectionId))
      .build();
  }

  @GetMapping("/basic/{sectionId}")
  public ApiResponse<SectionDto> getSectionBasicInfo(@PathVariable("sectionId") Long sectionId) {
    return ApiResponse.<SectionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Get section successfully")
      .result(sectionService.getSectionByIdBasicInfo(sectionId))
      .build();
  }

  @GetMapping
  public ApiResponse<SectionDto> deleteSection(@RequestParam String sectionName) {
    return ApiResponse.<SectionDto>builder()
      .code(AppError.SUCCESS.getCode())
      .message("Delete section successfully")
      .result(sectionService.getSectionByName(sectionName))
      .build();
  }
}
