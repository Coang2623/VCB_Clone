package com.vcb.backend.repository;

import com.vcb.backend.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  boolean existsBySectionName(String sectionName);

  Section findBySectionName(String sectionName);
}
