package com.develhope.spring.user;

import com.develhope.spring.loginSignup.LoginCredentials;
import com.develhope.spring.admin.*;
import com.develhope.spring.client.*;
import com.develhope.spring.seller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserEntity createUser(UserEntity user) {
        switch (user.getType()) {
            case CLIENT -> {
                ClientEntity client = new ClientEntity();
                client.setName(user.getName());
                client.setSurname(user.getSurname());
                client.setEmail(user.getEmail());
                client.setPsw(user.getPsw());
                client.setType(user.getType());

                return userRepository.save(client);
            }

            case SELLER -> {
                SellerEntity seller = new SellerEntity();
                seller.setName(user.getName());
                seller.setSurname(user.getSurname());
                seller.setEmail(user.getEmail());
                seller.setPsw(user.getPsw());
                seller.setType(user.getType());
                return userRepository.save(seller);
            }
            case ADMIN -> {
                AdminEntity admin = new AdminEntity();
                admin.setName(user.getName());
                admin.setSurname(user.getSurname());
                admin.setEmail(user.getEmail());
                admin.setPsw(user.getPsw());
                admin.setType(user.getType());
                return userRepository.save(admin);
            }
            default -> {
                return null;
            }
        }
    }


    public Optional<UserEntity> login(LoginCredentials loginCredentials) {
        for (UserEntity u : userRepository.findAll()) {

            boolean emailC1 = !(u.getEmail().isBlank());
            boolean emailC2 = (u.getEmail().equals(loginCredentials.getEmail()));
            boolean pswC1 = !(u.getPsw().isBlank());
            boolean pswC2 = (u.getPsw().equals(loginCredentials.getPsw()));

            if (emailC1 && emailC2 && pswC1 && pswC2) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
