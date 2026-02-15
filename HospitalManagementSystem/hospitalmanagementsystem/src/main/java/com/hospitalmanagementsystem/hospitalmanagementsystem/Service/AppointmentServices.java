package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.PatientRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
public class AppointmentServices {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> bookAppointment(Appointment appointment, Long patientId, Long doctorId) {
        try {
            Patient patient = patientRepository.findById(patientId).orElse(null);
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

            if (patient == null || doctor == null) {
                return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);}

            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            patient.getAppointment().add(appointment);
            Appointment newAppointment = appointmentRepository.save(appointment);

            return new ResponseEntity<>("Appointment booked successfully with Id:" + newAppointment.getId(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed to book an appointment", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR') OR hasAuthority('appointment:delete')")
    public ResponseEntity<String> cancleAppointment(Long appointmentId){
        try {
            appointmentRepository.deleteById(appointmentId);

            return new ResponseEntity<>("Appointment is deleted successfully", HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed to delete appointment",HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN') OR hasAuthority('appointment:write') OR #doctorId == Authentication.principal.id")
    public ResponseEntity<String> reassignAppointmentToNewDoctor(Long appointmentId, Long doctorId){
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

            if (appointment == null || doctor == null)
                throw new IllegalArgumentException("Invalid information for reassignAppointment.");

            appointment.setDoctor(doctor);

            return new ResponseEntity<>("reassing successfully.", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Reassigning failed",HttpStatus.BAD_REQUEST);
    }

}
