package com.develhope.spring.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@SuperBuilder
@Schema(name = "User Account", description = "User Account")
public class UserEntity implements UserDetails {
    @Schema(description = "Id", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name", example = "Mario", required = true)
    @Column(nullable = false)
    private String name;

    @Schema(description = "Surname", example = "Rossi", required = true)
    @Column(nullable = false)
    private String surname;

    @Schema(description = "Email", example = "mario.rossi@gmail.com", required = true)
    @Column(nullable = false, unique = true)
    private String email;

    @Schema(description = "Password", example = "password", required = true)
    @Column(nullable = false)
    private String psw;

    @Schema(description = "Type", example = "ADMIN", required = true)
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserType type;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getPsw();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
