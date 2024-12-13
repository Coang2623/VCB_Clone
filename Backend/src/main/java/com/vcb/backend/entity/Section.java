package com.vcb.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

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
  }

  @PreUpdate
  protected void onUpdate() {
    this.sectionUpdatedAt = LocalDateTime.now();
  }
}
