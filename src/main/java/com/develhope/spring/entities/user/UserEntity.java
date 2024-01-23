package com.develhope.spring.entities.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String psw;


}
