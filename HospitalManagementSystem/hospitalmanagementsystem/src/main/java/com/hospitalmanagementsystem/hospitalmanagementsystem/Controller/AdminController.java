package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.DoctorRegisterDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.DoctorService;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;


    @GetMapping("get-patients")
    public ResponseEntity<List<Patient>> getAllPatient(){
        return patientService.getAllPatients();
    }

    @PostMapping("promote-To-Doc/{doctorId}")
    public ResponseEntity<String> onBoardToDoctor(@RequestBody DoctorRegisterDTO doctor, @PathVariable Long doctorId){
        return doctorService.promoteToDoctor(doctor,doctorId);
    }
}
