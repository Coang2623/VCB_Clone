package com.vcb.backend.repository;

import com.vcb.backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
  boolean existsByPermissionName(String permissionName);

  Permission findByPermissionName(String permissionName);
}
