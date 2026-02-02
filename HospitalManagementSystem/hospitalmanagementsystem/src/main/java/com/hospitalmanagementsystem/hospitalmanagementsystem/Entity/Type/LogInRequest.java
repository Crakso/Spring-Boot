package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogInRequest {

    @Column(unique = true)
    private String username;
    private String password;
}
