package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;


import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.DoctorRegisterDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;


    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
           List<Doctor> alldoctor =  doctorRepository.findAll();
            return new ResponseEntity<>(alldoctor,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<String> promoteToDoctor(DoctorRegisterDTO doctor, Long doctorId) {
        try {
            User user = userRepository.findById(doctorId).orElse(null);
            if (user==null) {
                throw new IllegalArgumentException("User not exist.");
            }
            if (doctorRepository.existsById(user.getId())){
                throw new IllegalArgumentException("This user id is already a Doctor.");
            }

            user.getRoles().add(RoleType.DOCTOR);
            doctorRepository.save(Doctor.builder()
                    .email(user.getUsername())
                    .user(user)
                    .name(doctor.getName())
                    .specialization(doctor.getSpecialization())
                            .build());
        return new ResponseEntity<>("User is promoted to Doctor Successfully.",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
    }
}
