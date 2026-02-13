package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServices {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    public ResponseEntity<String> bookAppointment(Appointment appointment, Long patientId, Long doctorId) {

        Patient patient = patientRepository.findById(patientId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if(patient == null || doctor == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment newAppointment = appointmentRepository.save(appointment);

        if(newAppointment!=null) return
                new ResponseEntity<>("Appointment booked successfully", HttpStatus.OK);


        return new ResponseEntity<>("Appointment booked failed", HttpStatus.BAD_REQUEST);
    }

}
