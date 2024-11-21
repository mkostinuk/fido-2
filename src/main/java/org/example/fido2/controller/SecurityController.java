package org.example.fido2.controller;

import org.example.fido2.domain.dto.SignInRequestBean;
import org.example.fido2.domain.dto.SignUpRequestBean;
import org.example.fido2.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestBean request) {
        try {
            securityService.register(request);
            return ResponseEntity.ok("You are successfully registered");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestBean request){
        String token = null;
        try{
            token = securityService.signIn(request);
            return ResponseEntity.ok(token);
        }
        catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body("Bad credentials");
        }
    }
}
