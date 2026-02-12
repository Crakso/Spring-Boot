package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateRequestDTO {

    private String name;
    private String username;
    private String gender;
    private LocalDate dateOfBirth;

}
