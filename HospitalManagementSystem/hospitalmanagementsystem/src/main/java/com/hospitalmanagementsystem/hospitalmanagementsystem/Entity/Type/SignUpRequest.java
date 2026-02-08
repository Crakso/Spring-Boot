package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String username;
    private String password;
}
