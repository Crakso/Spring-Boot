package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {
    private String jwt;
    private Long id;
}
