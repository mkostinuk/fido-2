package org.example.fido2.domain.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
