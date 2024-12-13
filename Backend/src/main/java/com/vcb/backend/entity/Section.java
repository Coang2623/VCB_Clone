package com.vcb.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

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
public class Section {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long sectionId;

  @Column(name = "section_name", nullable = false, unique = true, length = 50)
  String sectionName;

  @Column(name = "section_description", nullable = false, length = 100)
  String sectionDescription;

  @Column(updatable = false)
  LocalDateTime sectionCreatedAt;

  @Column(updatable = false)
  String sectionCreatedBy;

  LocalDateTime sectionUpdatedAt;

  String sectionUpdatedBy;

  @PrePersist
  protected void onCreate() {
    this.sectionCreatedAt = LocalDateTime.now();
    this.sectionUpdatedAt = LocalDateTime.now();
    this.sectionCreatedBy = getUsernameFromToken();
    this.sectionUpdatedBy = getUsernameFromToken();
  }

  @PreUpdate
  protected void onUpdate() {
    this.sectionUpdatedAt = LocalDateTime.now();
    this.sectionUpdatedBy = getUsernameFromToken();
  }

  public String getUsernameFromToken() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return jwt.getSubject(); // Thường chứa username
  }
}
