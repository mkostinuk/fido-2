package org.example.fido2.service;

import org.example.fido2.domain.model.Role;
import org.example.fido2.security.jwt.JwtCore;
import org.example.fido2.dao.UserRepository;
import org.example.fido2.domain.dto.SignInRequestBean;
import org.example.fido2.domain.dto.SignUpRequestBean;
import org.example.fido2.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityService(AuthenticationManager authenticationManager, JwtCore jwtCore, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signIn(SignInRequestBean request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtCore.generateToken(authentication);
    }
    public void register(SignUpRequestBean request) {
        if(request==null){
            throw new IllegalArgumentException("All fields must be filled out");
        }
        if(repository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException(String.format("Username [%s] already exists", request.getUsername()));
        }
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .role(Role.USER)
                .build();
        repository.save(user);
    }

}