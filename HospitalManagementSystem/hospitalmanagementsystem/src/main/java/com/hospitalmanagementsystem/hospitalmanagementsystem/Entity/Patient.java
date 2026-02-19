package com.hospitalmanagementsystem.hospitalmanagementsystem.Entity;


import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "patient_tbl")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false, length = 40)
    private String name;

    @ToString.Exclude
    private LocalDate DateOfBirth;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne(mappedBy = "patient",cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Insurance insurance;

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Appointment> appointment;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
