package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class PatientService {
    private final PatientRepository patientRepo;


    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR') || hasAuthority('patient:read')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        try{
            List<Patient> patients = patientRepo.findAll();
            return new ResponseEntity<>(patients,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}
