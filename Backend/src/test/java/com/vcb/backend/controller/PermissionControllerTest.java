package com.vcb.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vcb.backend.dto.PermissionDto;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.service.impl.PermissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
class PermissionControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private PermissionServiceImpl permissionService;
//
//  private PermissionDto permissionDtoReq;
//  private PermissionDto permissionDtoRes;
//
//  @BeforeEach
//  void initData() {
//    permissionDtoReq = PermissionDto.builder()
//      .permissionName("DELETE_USER")
//      .permissionDescription("Delete user")
//      .build();
//
//    permissionDtoRes = PermissionDto.builder()
//      .permissionName("DELETE_USER")
//      .permissionDescription("Delete user")
//      .build();
//  }
//
//  @Test
//  void createPermission_validRequest_success() throws Exception {
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(permissionDtoReq);
//
//    //Mock service
//    when(permissionService.createPermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create permission successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.permissionName").value("DELETE_USER"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.permissionDescription").value("Delete user"));
//  }
//
//  @Test
//  void createPermission_nullPermissionName_fail() throws Exception {
//    //Set invalid request with null permissionName
//    var req = permissionDtoReq;
//    req.setPermissionName(null);
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(req);
//
//    //Mock service
//    when(permissionService.createPermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.PERMISSION_NAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.PERMISSION_NAME_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void createPermission_nullPermissionDescription_success() throws Exception {
//    //Set invalid request with null permissionDescription
//    var req = permissionDtoReq;
//    req.setPermissionDescription(null);
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(req);
//
//    //Mock service
//    when(permissionService.createPermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create permission successfully"));
//  }
//
//  @Test
//  void createPermission_emptyPermissionDescription_success() throws Exception {
//    //Set invalid request with empty permissionDescription
//    var req = permissionDtoReq;
//    req.setPermissionDescription("");
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(req);
//
//    //Mock service
//    when(permissionService.createPermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Create permission successfully"));
//  }
//
//  @Test
//  void createPermission_emptyPermissionName_fail() throws Exception {
//    //Set invalid request with empty permissionName
//    var req = permissionDtoReq;
//    req.setPermissionName("");
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(req);
//
//    //Mock service
//    when(permissionService.createPermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.PERMISSION_NAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.PERMISSION_NAME_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void updatePermission_validRequest_success() throws Exception {
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(permissionDtoReq);
//
//    //Mock service
//    when(permissionService.updatePermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .put("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Update permission successfully"));
//  }
//
//  @Test
//  void updatePermission_nullPermissionName_fail() throws Exception {
//    //Set invalid request with null permissionName
//    var req = permissionDtoReq;
//    req.setPermissionName(null);
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(req);
//
//    //Mock service
//    when(permissionService.updatePermission(any())).thenReturn(permissionDtoRes);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .put("/permission")
//      .contentType("application/json")
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.PERMISSION_NAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.PERMISSION_NAME_REQUIRED.getMessage()));
//  }

}
