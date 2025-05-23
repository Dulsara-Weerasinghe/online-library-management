package com.Assessment.Library_management.repository;

import com.Assessment.Library_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByuserId(String userId);

   Optional<User> findByUserName(String userName);
}
