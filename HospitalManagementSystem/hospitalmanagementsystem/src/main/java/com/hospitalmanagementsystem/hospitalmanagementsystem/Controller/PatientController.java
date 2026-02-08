package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PatientController {

    private final DoctorService doctorService;

    @GetMapping("get-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctor(){
        return doctorService.getAllDoctors();
    }

}
