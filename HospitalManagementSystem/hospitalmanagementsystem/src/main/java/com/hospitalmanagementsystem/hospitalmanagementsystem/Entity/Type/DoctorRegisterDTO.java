package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DoctorRegisterDTO {

    private String name;
    @Column(nullable = false)
    private String specialization;

}
