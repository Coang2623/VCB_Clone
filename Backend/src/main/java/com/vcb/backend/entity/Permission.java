package com.vcb.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
  @Id
  @Column(nullable = false, length = 30, unique = true)
  String permissionName;

  //Permission description
  @Column(length = 100)
  String permissionDescription;

  @ManyToMany(mappedBy = "permissions")
  Set<Role> roles;

  //Permission created date
  @Column(updatable = false)
  LocalDateTime permissionCreatedAt;

  //Id of user who created the permission
  @Column(updatable = false)
  String permissionCreatedBy;

  //Permission updated date
  LocalDateTime permissionUpdatedAt;

  //Id of user who updated the permission
  String permissionUpdatedBy;

  @PrePersist
  protected void onCreate() {
    this.permissionCreatedAt = LocalDateTime.now();
    this.permissionUpdatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.permissionUpdatedAt = LocalDateTime.now();
  }
}
