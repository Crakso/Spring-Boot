package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;


import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    public ResponseEntity<BigDecimal> getTotalNumberOfDoctor(){
        try{
            return new ResponseEntity<>(doctorRepository.countTotalDoctor(), HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<BigDecimal> getTotalNumberOfPatients(){
        try{
            return new ResponseEntity<>(patientRepository.totalNoOfPatient(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Patient>> getAllPatientArriveToday(){
        try{
            return new ResponseEntity<>(patientRepository.getAllPatientsArrivedToday(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<BigDecimal> countTotalPatientArrivedToday(){
        try{
            return new ResponseEntity<>(patientRepository.countAllPatientsArrivedToday(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<BigDecimal> countAllAppointmentsRegisteredToday(){
        try{
            return new ResponseEntity<>(appointmentRepository.countAllAppointmentRegisteredToday(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Appointment>> getAllAppointmentRegisteredToday(){
        try{
            return new ResponseEntity<>(appointmentRepository.getAllAppointmentRegisteredToday(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
