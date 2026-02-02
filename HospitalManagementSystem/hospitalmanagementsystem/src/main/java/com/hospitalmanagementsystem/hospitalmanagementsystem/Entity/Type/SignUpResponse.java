package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SignUpResponse {

    private Long id;
    private String username;
}
