package com.hospitalmanagementsystem.hospitalmanagementsystem.Service;


import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.DoctorRegisterDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.User;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.DoctorRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.UserRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class DoctorService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;


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
    @PreAuthorize("hasRole('ADMIN') OR hasAuthority('user:manage')")
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

    @Transactional
    @PreAuthorize("hasRole('ADMIN') OR hasAuthority('user:manage')")
    public ResponseEntity<String> removeDoctor(Long doctorId){
        try{
            if(!doctorRepository.existsById(doctorId)) throw new UsernameNotFoundException("Doctor not exist with this Id "+doctorId);

            doctorRepository.deleteById(doctorId);

            User user = userRepository.findById(doctorId).orElse(null);

            if(user==null) throw new UsernameNotFoundException("user not exist with this username");

            user.getRoles().remove(RoleType.DOCTOR);
            return new ResponseEntity<>("Doctor is deleted successfully.",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }


    @PreAuthorize("hasRole('ADMIN') OR #doctorId == Authentication.principal.id")
    public ResponseEntity<Doctor> getDoctorById(Long doctorId) {
        try {
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
            if (doctor == null) {
                return new ResponseEntity<>(new Doctor(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Doctor(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            Doctor doctor = getCurrentDoctor();
            List<Appointment> AppointmentsList = appointmentRepository.findAllAppointments(doctor);
            return new ResponseEntity<>(AppointmentsList, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Appointment>> getAppointmentBetweenDate(LocalDate startAt, LocalDate endAt){
        try{
            Doctor doctor = getCurrentDoctor();
            List<Appointment> allAppointmentBtwDate = appointmentRepository.findByDoctorAndDateBetween(doctor,startAt,endAt);
            return new ResponseEntity<>(allAppointmentBtwDate,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<List<Appointment>> getTop5Appointment(){
        Doctor doctor = getCurrentDoctor();
        List<Appointment> appointment = appointmentRepository.findTop5ByDoctorOrderByDateDesc(doctor);
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }


    public Doctor getCurrentDoctor(){
        User user = authUtil.getCurrentUser();
        if(user==null){
            throw new UsernameNotFoundException("Invalid User.");
        }
        Doctor doctor = doctorRepository.findById(user.getId()).orElseThrow(() ->
                new UsernameNotFoundException("Doctor not found."));
        return doctor;
    }

}
