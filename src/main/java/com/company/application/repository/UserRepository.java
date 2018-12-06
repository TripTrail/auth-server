package com.company.application.repository;

import com.company.application.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends  JpaRepository<UserDetail, UUID> {
  UserDetail findByEmailId(String emailId);
}
