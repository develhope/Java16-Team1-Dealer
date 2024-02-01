package com.develhope.spring.services;

import com.develhope.spring.entities.user.AdminEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.repositories.UserRepository;
import org.apache.catalina.User;
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


    public Boolean checkEmail(String email) {
        for (UserEntity u : userRepository.findAll()) {
            if (u.getEmail().equals(email) && !(u.getEmail().isBlank())) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkPsw(String psw) {
        for (UserEntity u : userRepository.findAll()) {
            if (u.getPsw().equals(psw) && !(u.getPsw().isBlank())) {
                return true;
            }
        }
        return false;
    }
}
