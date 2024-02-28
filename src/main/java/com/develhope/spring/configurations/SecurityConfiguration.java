package com.develhope.spring.configurations;

import com.develhope.spring.user.UserService;
import com.develhope.spring.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final IdLogin idLogin;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v1/user/**").permitAll()
                        .requestMatchers("/v1/client/**").hasAuthority(UserType.CLIENT.name())
                        .requestMatchers("/v1/admin/**").hasAuthority(UserType.ADMIN.name())
                        .requestMatchers("/v1/seller/**").hasAuthority(UserType.SELLER.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        if(idLogin.getType().equals(UserType.CLIENT.toString())){
//            http.csrf(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests(request -> request
//                            .requestMatchers("/v1/client/**").permitAll()
//                            .anyRequest().authenticated())
//                    .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                    .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        } else if (idLogin.getType().equals(UserType.ADMIN.toString())) {
//            http.csrf(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests(request -> request
//                            .requestMatchers("/v1/admin/**").permitAll()
//                            .anyRequest().authenticated())
//                    .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                    .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        } else if (idLogin.getType().equals(UserType.SELLER.toString())) {
//            http.csrf(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests(request -> request
//                            .requestMatchers("/v1/seller/**").permitAll()
//                            .anyRequest().authenticated())
//                    .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                    .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        }else {
//            http.csrf(AbstractHttpConfigurer::disable)
//                    .authorizeHttpRequests(request -> request
//                            .requestMatchers("/v1/user/**").permitAll()
////                        .requestMatchers("/v1/client/**").hasAuthority()
////                        .requestMatchers("/v1/admin/**").hasAuthority(UserType.ADMIN.name())
////                        .requestMatchers("/v1/seller/**").hasAuthority(UserType.SELLER.name())
//                            .anyRequest().authenticated())
//                    .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                    .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//            return http.build();
//        }
//    }







    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
