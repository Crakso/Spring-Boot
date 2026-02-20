package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Repository.AppointmentRepository;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.AppointmentServices;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentServices appointmentServices;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointment(){
        return appointmentServices.getAllAppointments(doctorService.getCurrentDoctor());
    }
}
