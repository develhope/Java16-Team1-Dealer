package com.develhope.spring.user;

import com.develhope.spring.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Since email is unique, we'll find users by email
    Optional<UserEntity> findByEmail(String email);

}
