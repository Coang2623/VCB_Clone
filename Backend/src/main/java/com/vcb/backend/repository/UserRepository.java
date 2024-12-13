package com.vcb.backend.repository;

import com.vcb.backend.entity.User;
import com.vcb.backend.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUserName(String userName);

  Optional<User> findByUserName(String userName);

  @Query("SELECT u FROM User u WHERE u.userStatus <> :status")
  List<User> findUsersWithStatusNot(@Param("status")UserStatusEnum status);
}
