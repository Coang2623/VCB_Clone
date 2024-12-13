package com.vcb.backend.entity;

import com.vcb.backend.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1, initialValue = 1000)
  @Column(nullable = false, unique = true)
  Long userId;

  //User login name
  @Column(nullable = false, length = 50, unique = true)
  String userName;

  //Hashed Password
  @Column(nullable = false, length = 128)
  String userPassword;

  @Column(length = 50)
  String userFirstName;

  @Column(length = 50)
  String userLastName;

  @Column(length = 50)
  String userEmail;

  LocalDate userDateOfBirth;

  @Enumerated(EnumType.STRING)
  private UserStatusEnum userStatus;

  @ManyToMany(
    targetEntity = Role.class
  )
  @JoinTable(
    name = "User_Role",  // Define join table name
    joinColumns = @JoinColumn(name = "userId"),  // Define join column for Employee
    inverseJoinColumns = @JoinColumn(name = "roleName")  // Define join column for Project
  )
  Set<Role> roles;

  // Thời gian tạo bản ghi
  @Column(updatable = false)
  LocalDateTime userCreatedAt;

  // Người tạo bản ghi
  @Column(length = 50, updatable = false)
  String userCreatedBy;

  // Thời gian cập nhật cuối cùng
  @Column()
  LocalDateTime userUpdatedAt;

  // Người cập nhật cuối cùng
  @Column(length = 50)
  String userUpdatedBy;

  // Ghi lại thời gian tạo trước khi lưu bản ghi đầu tiên
  @PrePersist
  protected void onCreate() {
    this.userCreatedAt = LocalDateTime.now();
    this.userUpdatedAt = LocalDateTime.now();
  }

  // Cập nhật thời gian khi có thay đổi
  @PreUpdate
  protected void onUpdate() {
    this.userUpdatedAt = LocalDateTime.now();
  }
}
