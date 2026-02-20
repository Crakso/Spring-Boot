package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Appointment;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT COUNT(a) FROM Appointment a")
    BigDecimal getTotalAppointment();

    @Query("SELECT a FROM Appointment a WHERE a.doctor=:doctor")
    List<Appointment> findAllAppointments(@Param("doctor") Doctor doctor);

    List<Appointment> findTop5ByDoctorOrderByCreatedAtDesc(Doctor doctor);

    List<Appointment> findByDoctorAndCreatedAtBetween(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE FUNCTION('DATE', a.createdAt) = CURRENT_DATE")
    BigDecimal countAllAppointmentRegisteredToday();

    @Query("SELECT a FROM Appointment a WHERE FUNCTION('DATE', a.createdAt) = CURRENT_DATE")
    List<Appointment> getAllAppointmentRegisteredToday();

}