package com.springai.mcp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"patient_id", "visit_date"}))
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private LocalDate visitDate;

    private String diagnosis;

    private String treatment;

    private String notes;

    private String prescribedMedications;

    private String doctorName;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    @Version
    private Long version; // This field is used for optimistic locking
}
