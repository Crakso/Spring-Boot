package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Insurance;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.InsuranceRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class InsuranceService {

    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;

    public InsuranceService(PatientRepository patientRepository,
                            InsuranceRepository insuranceRepository) {
        this.patientRepository = patientRepository;
        this.insuranceRepository = insuranceRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createInsurance(Insurance insurance, Long patientId){
        try {
            Patient patient = patientRepository.findById(patientId).orElseThrow();
            if (patient.getInsurance() != null) throw new RuntimeException("patient already have an insurance.");
            insurance.setPatient(patient);
            insuranceRepository.save(insurance);
            patient.setInsurance(insurance);
            return new ResponseEntity<>("Insurance is created successfully.", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);

    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteInsurance(Long insuranceId) {
        try {
            Insurance insurance = insuranceRepository.findById(insuranceId).orElse(null);
            if (insurance == null) {
                throw new IllegalArgumentException("Invalid Insurance. Insurance not found.");
            }
//            insuranceRepository.delete(insurance);
            Patient patient = insurance.getPatient();
            if(patient!=null){
                patient.setInsurance(null);
            }
            return new ResponseEntity<>("Insurance is removed successfully.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Insurance> getInsurance(Long insuranceId){
        try {
            Insurance insurance = insuranceRepository.findById(insuranceId).orElse(null);
            if(insurance==null){
                return new ResponseEntity<>(new Insurance(),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(insurance,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
