package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT COUNT(a) FROM Appointment a")
    BigDecimal getTotalAppointment();

//    @Query("SELECT a FROM Appointment a WHERE a.createdAt>=CURRENT_DATE AND a.createdAt<CURRENT_DATE+1")
//    List<Appointment> getTodayAppointments();
//
//    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.createdAt>=CURRENT_DATE AND a.createdAt<CURRENT_DATE+1")
//    BigDecimal countTodayAppointments();
}