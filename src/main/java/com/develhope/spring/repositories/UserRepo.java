package com.develhope.spring.repositories;

import com.develhope.spring.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
