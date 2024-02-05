package com.develhope.spring.repositories;

import com.develhope.spring.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
