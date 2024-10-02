package org.example.fido2.domain.dto;

import lombok.Data;

@Data
public class SignInRequestBean {
    private String username;
    private String password;
}
