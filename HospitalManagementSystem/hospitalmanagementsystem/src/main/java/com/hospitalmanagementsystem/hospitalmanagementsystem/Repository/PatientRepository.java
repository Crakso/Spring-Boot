package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("SELECT COUNT(p) FROM Patient p")
    BigDecimal totalNoOfPatient();

    @Query("SELECT p FROM Patient p WHERE FUNCTION('DATE', p.createdAt) = CURRENT_DATE")
    List<Patient> getAllPatientsArrivedToday();

//    select u from User u where u.timestamp > date_trunc('day', now());

    @Query("SELECT COUNT(p) FROM Patient p WHERE FUNCTION('DATE', p.createdAt) = CURRENT_DATE")
    BigDecimal countAllPatientsArrivedToday();

//@Query()
//List<Patient> getPatientsBetweenDate(LocalDateTime from, LocalDateTime to);


}