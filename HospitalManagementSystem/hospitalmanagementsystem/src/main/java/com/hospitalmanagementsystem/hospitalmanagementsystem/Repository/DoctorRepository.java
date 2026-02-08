package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}