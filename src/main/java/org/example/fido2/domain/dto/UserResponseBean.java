package org.example.fido2.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseBean {
    private String username;
    private String name;
    private int age;
}
