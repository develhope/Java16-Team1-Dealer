package com.develhope.spring.services;

import com.develhope.spring.entities.user.AdminEntity;
import com.develhope.spring.entities.user.ClientEntity;
import com.develhope.spring.entities.user.SellerEntity;
import com.develhope.spring.entities.user.UserEntity;
import com.develhope.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static java.awt.geom.Path2D.contains;


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


    public void checkEmail(UserEntity user) {

        Optional< UserEntity> toCheckUser = userRepository.findById(user.getId());
        if(userRepository.existsById(user.getId())) {

        }
    }
}
