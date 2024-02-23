package com.develhope.spring.user;

import com.develhope.spring.loginSignup.IdLogin;
import com.develhope.spring.loginSignup.LoginCredentials;
import com.develhope.spring.admin.*;
import com.develhope.spring.client.*;
import com.develhope.spring.seller.*;
import com.develhope.spring.user.userControllerResponse.CreateNewAccountResponse;
import com.develhope.spring.user.userControllerResponse.ErrorMessageUser;
import com.develhope.spring.user.userControllerResponse.LoginAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ErrorMessageUser errorMessageUser;
    @Autowired
    private IdLogin idLogin;


    public Boolean checkEmail(String email) {
        for(UserEntity u : userRepository.findAll()) {
            if(u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }



    public ResponseEntity<CreateNewAccountResponse> createUser(UserEntity user) {
        switch (user.getType()) {
            case CLIENT -> {
                ClientEntity client = new ClientEntity();
                client.setName(user.getName());
                client.setSurname(user.getSurname());
                if(checkEmail(user.getEmail())) {
                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist(), null);
                    return ResponseEntity.status(600).body(createNewAccountResponse);
                }else{
                    client.setEmail(user.getEmail());
                }
                client.setEmail(user.getEmail());
                client.setPsw(user.getPsw());
                client.setType(user.getType());

                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.clienCreate(), userRepository.save(client));
                return ResponseEntity.status(601).body(createNewAccountResponse);
            }

            case SELLER -> {
                SellerEntity seller = new SellerEntity();
                seller.setName(user.getName());
                seller.setSurname(user.getSurname());
                if(checkEmail(user.getEmail())) {
                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist(), null);
                    return ResponseEntity.status(600).body(createNewAccountResponse);
                }else{
                    seller.setEmail(user.getEmail());
                }

                seller.setPsw(user.getPsw());
                seller.setType(user.getType());

                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.sellerCreate(), userRepository.save(seller));
                return ResponseEntity.status(602).body(createNewAccountResponse);
            }
            case ADMIN -> {
                AdminEntity admin = new AdminEntity();
                admin.setName(user.getName());
                admin.setSurname(user.getSurname());
                if(checkEmail(user.getEmail())) {
                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist(), null);
                    return ResponseEntity.status(600).body(createNewAccountResponse);
                }else{
                    admin.setEmail(user.getEmail());
                }
                admin.setPsw(user.getPsw());
                admin.setType(user.getType());

                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.adminCreate(), userRepository.save(admin));
                return ResponseEntity.status(603).body(createNewAccountResponse);
            }
            default -> {
                return null;
            }
        }
    }


    public ResponseEntity<LoginAccountResponse> login(LoginCredentials loginCredentials) {
        for (UserEntity u : userRepository.findAll()) {

            boolean emailC1 = !(u.getEmail().isBlank());

            boolean emailC2 = (u.getEmail().equals(loginCredentials.getEmail()));
            boolean pswC1 = !(u.getPsw().isBlank());

            boolean pswC2 = (u.getPsw().equals(loginCredentials.getPsw()));
            if(loginCredentials.getEmail().isBlank()){
                LoginAccountResponse loginAccountResponse = new LoginAccountResponse(errorMessageUser.emailBlank());
                return ResponseEntity.status(604).body(loginAccountResponse);
            }
            if(loginCredentials.getPsw().isBlank()){
                LoginAccountResponse loginAccountResponse = new LoginAccountResponse(errorMessageUser.pswBlank());
                return ResponseEntity.status(606).body(loginAccountResponse);
            }
            if (emailC1 && emailC2 && pswC1 && pswC2) {
                idLogin.setId(u.getId());
                idLogin.setType(u.getType().toString());
                LoginAccountResponse loginAccountResponse = new LoginAccountResponse(errorMessageUser.okLogin(u.getEmail()));
                return ResponseEntity.status(200).body(loginAccountResponse);
            }
        }
        LoginAccountResponse loginAccountResponse = new LoginAccountResponse(errorMessageUser.errorLogin());
        return ResponseEntity.status(605).body(loginAccountResponse);
    }
}
