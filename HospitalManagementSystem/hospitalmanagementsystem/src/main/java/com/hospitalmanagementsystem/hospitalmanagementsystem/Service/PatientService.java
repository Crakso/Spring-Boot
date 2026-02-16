package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.UpdateRequestDTO;
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

    @PreAuthorize("hasAnyRole('ADMIN,'DOCTOR' OR hasAuthority('patient:read') OR #id == authentication.principal.id)")
    public ResponseEntity<Patient> getPatientById(Long id){
        try{
           Patient patinet =  patientRepo.findById(id).orElse(null);

           if(patinet==null){
               return new ResponseEntity<>(new Patient(),HttpStatus.NOT_FOUND);
           } return new ResponseEntity<>(patinet,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Patient(),HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN,'DOCTOR' OR hasAuthority('patient:read') OR #id == authentication.principal.id)")
    public ResponseEntity<String> deletePatientById(Long id){
        try{
            patientRepo.deleteById(id);
            return new ResponseEntity<>("Patient is deleted successfully.",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> updatePatientById(Long id, UpdateRequestDTO patient){
        try{
            Patient existingPatient = patientRepo.findById(id).orElse(null);
            if(existingPatient==null){
                return new ResponseEntity<>(new Patient(),HttpStatus.NOT_FOUND);
            }
            existingPatient.setName(patient.getName());
            existingPatient.setEmail(patient.getUsername());
            existingPatient.setGender(patient.getGender());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());
            Patient updatedPatient = patientRepo.save(existingPatient);
            return new ResponseEntity<>(updatedPatient,HttpStatus.OK);

    } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Patient(),HttpStatus.BAD_REQUEST);
    }

}
