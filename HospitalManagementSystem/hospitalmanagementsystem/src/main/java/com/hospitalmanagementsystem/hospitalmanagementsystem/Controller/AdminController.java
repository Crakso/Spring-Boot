package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.DoctorRegisterDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.UpdateRequestDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.DoctorService;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("get-patient/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        return patientService.deletePatientById(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Patient> updatePatientById(@PathVariable Long id, @RequestBody UpdateRequestDTO patient) {
        return patientService.updatePatientById(id, patient);
    }

    @PostMapping("promote-To-Doc/{doctorId}")
    public ResponseEntity<String> onBoardToDoctor(@RequestBody DoctorRegisterDTO doctor, @PathVariable Long doctorId){
        return doctorService.promoteToDoctor(doctor,doctorId);
    }



}
