package com.hospitalmanagementsystem.hospitalmanagementsystem.Repository;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}