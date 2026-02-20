package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;


import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@EnableMethodSecurity
public class AdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public AdminService(UserRepository userRepository,
                        DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> promoteUserToAdmin(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) return new ResponseEntity<>("Invalid User.", HttpStatus.NOT_FOUND);
            user.getRoles().add(RoleType.ADMIN);
            userRepository.save(user);
            return new ResponseEntity<>("User promoted successfully.", HttpStatus.OK);
        }catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> demoteUserFromAdmin(Long id) {
        try{
            User user = userRepository.findById(id).orElse(null);
            if(user==null) return new ResponseEntity<>("Invalid User",HttpStatus.NOT_FOUND);
            user.getRoles().remove(RoleType.ADMIN);
            userRepository.save(user);
            return new ResponseEntity<>("Demoted Successfully.",HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
