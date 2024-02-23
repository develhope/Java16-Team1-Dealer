package com.develhope.spring.admin;

import com.develhope.spring.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Admin Accout", description = "Admin Account")
public class AdminEntity extends UserEntity {

}
