package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT COUNT(d) FROM Doctor d")
    BigDecimal countTotalDoctor();
}