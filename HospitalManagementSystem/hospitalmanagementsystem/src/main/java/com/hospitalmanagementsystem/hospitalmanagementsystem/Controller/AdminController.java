package com.hospitalmanagementsystem.hospitalmanagementsystem.Controller;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.DoctorRegisterDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.UpdateRequestDTO;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.AdminService;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Service.AppointmentServices;
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
    private final AppointmentServices appointmentServices;
    private final AdminService adminService;


    @GetMapping("get-patients")
    public ResponseEntity<List<Patient>> getAllPatient(){
        return patientService.getAllPatients();
    }

    // Manage Patients.

    @GetMapping("get-patient/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    @DeleteMapping("delete-patient/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        return patientService.deletePatientById(id);
    }

    @PutMapping("update-patient/{id}")
    public ResponseEntity<Patient> updatePatientById(@PathVariable Long id, @RequestBody UpdateRequestDTO patient) {
        return patientService.updatePatientById(id, patient);
    }



    // Manage Appointments.

    @PostMapping("book-appointment/{patientId}/{doctorId}")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment,@PathVariable Long patientId,@PathVariable Long doctorId) {
        return appointmentServices.bookAppointment(appointment,patientId,doctorId);
    }

    @DeleteMapping("cancel-appointment/{appointmentId}")
    public ResponseEntity<String> cancleAppointment(@PathVariable Long appointmentId){
        return appointmentServices.cancleAppointment(appointmentId);
    }

    @PutMapping("assingDoc/{appointmentId}/{doctorId}")
    public ResponseEntity<String> ressignAppointmentToNewDoctor(@PathVariable Long appointmentId,@PathVariable Long doctorId){
        return appointmentServices.reassignAppointmentToNewDoctor(appointmentId,doctorId);
    }

    //Manage Doctor

    @DeleteMapping("remove-doctor/{doctorId}")
    public ResponseEntity<String> removeDoctor(@PathVariable Long doctorId){
        return doctorService.removeDoctor(doctorId);
    }

    @PostMapping("promote-To-Doc/{doctorId}")
    public ResponseEntity<String> onBoardToDoctor(@RequestBody DoctorRegisterDTO doctor, @PathVariable Long doctorId){
        return doctorService.promoteToDoctor(doctor,doctorId);
    }

    @GetMapping("doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id);
    }



    //Manage ADMIN

    @PostMapping("promote/{id}")
    public ResponseEntity<String> promoteToAdmin(Long id){
        return adminService.promoteUserToAdmin(id);
    }

    @PostMapping("remove/{id}")
    public ResponseEntity<String> demoteAdmin(Long id){
        return adminService.demoteUserFromAdmin(id);
    }

















}
