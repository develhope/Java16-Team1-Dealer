package com.develhope.spring.loginSignup;

import com.develhope.spring.admin.AdminEntity;
import com.develhope.spring.client.ClientEntity;
import com.develhope.spring.seller.SellerEntity;
import com.develhope.spring.user.UserEntity;
import com.develhope.spring.user.UserRepository;
import com.develhope.spring.user.UserType;
import com.develhope.spring.user.userControllerResponse.CreateNewAccountResponse;
import com.develhope.spring.user.userControllerResponse.ErrorMessageUser;
import com.develhope.spring.user.userControllerResponse.LoginAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
//    public ResponseEntity<CreateNewAccountResponse> signup(UserEntity user) {
//        switch (user.getType()) {
//            case CLIENT -> {
//                ClientEntity client = new ClientEntity();
//                client.setName(user.getName());
//                client.setSurname(user.getSurname());
//                if(checkEmail(user.getEmail())) {
//                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist());
//                    return ResponseEntity.status(600).body(createNewAccountResponse);
//                }else{
//                    client.setEmail(user.getEmail());
//                }
//                client.setPsw(passwordEncoder.encode(user.getPsw()));
//                client.setType(user.getType());
//
//                String token = jwtService.generateToken(client);
//
//                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.clienCreate(), userRepository.save(client),token);
//                return ResponseEntity.status(601).body(createNewAccountResponse);
//            }
//
//            case SELLER -> {
//                SellerEntity seller = new SellerEntity();
//                seller.setName(user.getName());
//                seller.setSurname(user.getSurname());
//                if(checkEmail(user.getEmail())) {
//                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist());
//                    return ResponseEntity.status(600).body(createNewAccountResponse);
//                }else{
//                    seller.setEmail(user.getEmail());
//                }
//
//                seller.setPsw(passwordEncoder.encode(user.getPsw()));
//                seller.setType(user.getType());
//
//                String token = jwtService.generateToken(seller);
//
//                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.sellerCreate(), userRepository.save(seller), token);
//                return ResponseEntity.status(602).body(createNewAccountResponse);
//            }
//            case ADMIN -> {
//                AdminEntity admin = new AdminEntity();
//                admin.setName(user.getName());
//                admin.setSurname(user.getSurname());
//                if(checkEmail(user.getEmail())) {
//                    CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.emailExist());
//                    return ResponseEntity.status(600).body(createNewAccountResponse);
//                }else{
//                    admin.setEmail(user.getEmail());
//                }
//                admin.setPsw(passwordEncoder.encode(user.getPsw()));
//                admin.setType(user.getType());
//
//                String token = jwtService.generateToken(admin);
//
//                CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.adminCreate(), userRepository.save(admin), token);
//                return ResponseEntity.status(603).body(createNewAccountResponse);
//            }
//            default -> {
//                return null;
//            }
//        }
//    }
    @Override
    public ResponseEntity<CreateNewAccountResponse> signup(UserEntity user) {
        UserEntity user1 = UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .psw(passwordEncoder.encode(user.getPsw()))
                .type((user.getType())).build();

        var jwt = jwtService.generateToken(user);
        CreateNewAccountResponse createNewAccountResponse = new CreateNewAccountResponse(errorMessageUser.adminCreate(), userRepository.save(user), jwt);
        return ResponseEntity.status(603).body(createNewAccountResponse);
    }




    @Override
    public ResponseEntity<LoginAccountResponse> signin(LoginCredentials loginCredentials) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPsw()));
        }catch (Exception ex){
            System.out.println(ex);
        }

        //TODO Instead of only throw IllegalArgumentException maybe we could handle an error on the controller side
        UserEntity user = userRepository.findByEmail(loginCredentials.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(user);
        LoginAccountResponse loginAccountResponse = new LoginAccountResponse(errorMessageUser.okLogin(loginCredentials.getEmail()), jwt);

        idLogin.setType(user.getType().toString());
        System.out.println(idLogin.getType());
        return ResponseEntity.status(200).body(loginAccountResponse);
    }

}