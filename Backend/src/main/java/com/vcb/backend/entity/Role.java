package com.vcb.backend.entity;

import com.vcb.backend.service.AuthenticationService;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

  @Id
  @Column(nullable = false, unique = true, length = 30)
  String roleName;
  @Column(length = 100)
  String roleDescription;

  @ManyToMany(
    targetEntity = Permission.class
  )
  @JoinTable(
    name = "Role_Permission",  // Define join table name
    joinColumns = @JoinColumn(name = "roleName"),  // Define join column for Employee
    inverseJoinColumns = @JoinColumn(name = "permissionName")  // Define join column for Project
  )
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "roles")
  Set<User> user;

  @Column(updatable = false)
  LocalDateTime roleCreatedAt;
  @Column(updatable = false)
  String roleCreatedBy;
  @Column
  LocalDateTime roleUpdatedAt;
  @Column
  String roleUpdatedBy;

  @PrePersist
  void onCreate() {
    this.roleCreatedAt = LocalDateTime.now();
    this.roleUpdatedAt = LocalDateTime.now();
  }

  @PreUpdate
  void onUpdate() {
    this.roleUpdatedAt = LocalDateTime.now();
  }
}
