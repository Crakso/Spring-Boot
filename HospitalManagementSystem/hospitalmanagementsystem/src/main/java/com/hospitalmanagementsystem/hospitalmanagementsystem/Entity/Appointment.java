package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false) // Patient is required and not nullable.
    @ToString.Exclude   // patient try to find appointment and appointment again find patient stack overflow error that why exclude patient.
    private Patient patient;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @JoinColumn(nullable = false)
//    private Doctor doctor;
}
