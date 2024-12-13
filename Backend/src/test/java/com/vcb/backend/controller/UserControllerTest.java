package com.vcb.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vcb.backend.dto.userDto.UserDto;
import com.vcb.backend.dto.userDto.UserFullResponse;
import com.vcb.backend.enums.UserStatusEnum;
import com.vcb.backend.exception.AppError;
import com.vcb.backend.exception.AppException;
import com.vcb.backend.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
class UserControllerTest {
//
//  //Inject MockMvc
//  @Autowired
//  private MockMvc mockMvc;
//
//  //Inject mock service
//  @MockBean
//  private UserServiceImpl userService;
//
//  private UserDto request;
//  private UserDto response;
//  private LocalDate dateOfBirth;
//
//  @BeforeEach
//  void initData() {
//
//    //Example of Valid request
//    dateOfBirth = LocalDate.of(1999, 1, 1);
//    request = UserDto.builder()
//      .userFirstName("Nguyen")
//      .userLastName("Phuoc")
//      .userName("nguyenphuoc")
//      .userPassword("12345678")
//      .userEmail("BkxkZ@example.com")
//      .userDateOfBirth(dateOfBirth)
//      .build();
//
//    //Expected response for example request
//    response = UserDto.builder()
//      .userId(1L)
//      .userFirstName("Nguyen")
//      .userLastName("Phuoc")
//      .userName("nguyenphuoc")
//      .userEmail("BkxkZ@example.com")
//      .userDateOfBirth(dateOfBirth)
//      .userStatus(UserStatusEnum.ACTIVE)
//      .userCreatedAt(null)
//      .userCreatedBy(null)
//      .userUpdatedAt(null)
//      .userUpdatedBy(null)
//      .build();
//  }
//
//  @Test
//  void createUser_validRequest_success() throws Exception {
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(request);
//
//    //Simulate response from service layer
//    when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//      .post("/user")
//      .contentType(MediaType.APPLICATION_JSON_VALUE)
//      .content(content))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("User created successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userId").value(1))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userFirstName").value("Nguyen"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userLastName").value("Phuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value("nguyenphuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userEmail").value("BkxkZ@example.com"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userDateOfBirth").value("1999-01-01"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userStatus").value("ACTIVE"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userCreatedAt").doesNotExist())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userCreatedBy").doesNotExist())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userUpdatedAt").doesNotExist())
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userUpdatedBy").doesNotExist());
//  }
//
//  @Test
//  void createUser_nullUserName_fail() throws Exception {
//    //Set invalid request with null username
//    var req = request;
//    req.setUserName(null);
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.USERNAME_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.USERNAME_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void createUser_shortUserName_fail() throws Exception {
//    //Set invalid request with short username
//    var req = request;
//    req.setUserName("abc");
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.USERNAME_INVALID.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 5 characters long"));
//  }
//
//  @Test
//  void createUser_nullPassword_fail() throws Exception {
//    //Set invalid request with null password
//    var req = request;
//    req.setUserPassword(null);
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.PASSWORD_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.PASSWORD_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void createUser_shortPassword_fail() throws Exception {
//    //Set invalid request with short password
//    var req = request;
//    req.setUserPassword("123");
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.PASSWORD_INVALID.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 8 characters long"));
//  }
//
//  @Test
//  void createUser_nullEmail_fail() throws Exception {
//    //Set invalid request with null email
//    var req = request;
//    req.setUserEmail(null);
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.EMAIL_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.EMAIL_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void createUser_invalidEmail_fail() throws Exception {
//    //Set invalid request with invalid email
//    var req = request;
//    req.setUserEmail("abc");
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.EMAIL_INVALID.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.EMAIL_INVALID.getMessage()));
//  }
//
//  @Test
//  void createUser_nullDateOfBirth_fail() throws Exception {
//    //Set invalid request with null date of birth
//    var req = request;
//    req.setUserDateOfBirth(null);
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.DOB_REQUIRED.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.DOB_REQUIRED.getMessage()));
//  }
//
//  @Test
//  void createUser_invalidDateOfBirth_fail() throws Exception {
//    //Set invalid request with invalid date of birth
//    int currentYear = LocalDate.now().getYear();
//    int currentMonth = LocalDate.now().getMonthValue();
//    int currentDay = LocalDate.now().getDayOfMonth();
//    request.setUserDateOfBirth(LocalDate.of((currentYear - 17), currentMonth, currentDay - 1));
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(request);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.DOB_INVALID.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("You must be at least 18 years old"));
//  }
//
//  @Test
//  void createUser_invalidStatus_fail() throws Exception {
//    //Set invalid request with null status
//    var req = request;
//    req.setUserStatus(null);
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
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.USER_STATUS_INVALID.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.USER_STATUS_INVALID.getMessage()));
//  }
//
//  @Test
//  void createUser_nullRequest_fail() throws Exception {
//    //Set invalid request with null request
//    request = new UserDto();
//
//    //Create ObjectMapper
//    ObjectMapper mapper = new ObjectMapper();
//
//    //Register module to process LocalDate
//    mapper.registerModule(new JavaTimeModule());
//
//    //Convert object to json
//    String content = mapper.writeValueAsString(request);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .post("/user")
//        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        .content(content))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest());
//  }
//
//  //Get all users
//  @Test
//  void getAllUsers_success() throws Exception {
//
//    when(userService.getAllUsersWithFullInfo()).thenReturn(List.of(response));
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/user/all")
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Get all users successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userName").value("nguyenphuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userEmail").value("BkxkZ@example.com"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userFirstName").value("Nguyen"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userLastName").value("Phuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userStatus").value("ACTIVE"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.[0].userDateOfBirth").value("1999-01-01"));
//
//  }
//
//  //Get user by id
//  @Test
//  void getUserById_success() throws Exception {
//
//    when(userService.getUserWithFullInfo(ArgumentMatchers.anyLong())).thenReturn(response);
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/user/{id}", 1)
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isOk())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.SUCCESS.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value("Get user " + 1 + " with full info successfully"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userId").value(1))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userFirstName").value("Nguyen"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userLastName").value("Phuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value("nguyenphuoc"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userEmail").value("BkxkZ@example.com"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userDateOfBirth").value("1999-01-01"))
//      .andExpect(MockMvcResultMatchers.jsonPath("result.userStatus").value("ACTIVE"));
//  }
//
//  @Test
//  void getUserById_invalidId_fail() throws Exception {
//
//    when(userService.getUserWithFullInfo(ArgumentMatchers.anyLong())).thenThrow(new AppException(AppError.USER_NOT_EXIST));
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/user/{id}", -1)
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      .andExpect(MockMvcResultMatchers.jsonPath("code").value(AppError.USER_NOT_EXIST.getCode()))
//      .andExpect(MockMvcResultMatchers.jsonPath("message").value(AppError.USER_NOT_EXIST.getMessage()));
//  }
//
//  @Test
//  void getUserById_nullId_fail() throws Exception {
//
//    //Perform request to controller layer and check response
//    mockMvc.perform(MockMvcRequestBuilders
//        .get("/user/{id}", null)
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//      .andExpect(MockMvcResultMatchers.status().isBadRequest())
//      ;
//  }
}
