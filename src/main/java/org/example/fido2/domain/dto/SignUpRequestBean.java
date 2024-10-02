package org.example.fido2.domain.dto;

import lombok.Data;

@Data
public class SignUpRequestBean {
    private String username;
    private String name;
    private int age;
    private String password;
}
