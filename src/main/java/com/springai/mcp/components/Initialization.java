package com.springai.mcp.components;

import com.springai.mcp.domain.MedicalHistory;
import com.springai.mcp.domain.Patient;
import com.springai.mcp.domain.VisitType;
import com.springai.mcp.repository.MedicalHistoryRepository;
import com.springai.mcp.repository.PatientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Initialization {
    private final PatientRepository patientRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    @PostConstruct
    public void init() {
        // Create and save patients
        Patient john = patientRepository.save(Patient.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDate.now())
                .build());

        Patient jane = patientRepository.save(Patient.builder()
                .firstName("Jane")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1995, 5, 15))
                .registrationDate(LocalDate.now())
                .build());

        Patient mary = patientRepository.save(Patient.builder()
                .firstName("Mary")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1980, 3, 5))
                .registrationDate(LocalDate.now())
                .build());

        Patient bob = patientRepository.save(Patient.builder()
                .firstName("Bob")
                .lastName("Johnson") .dateOfBirth(LocalDate.of(1975, 7, 20))
                .registrationDate(LocalDate.now())
                .build());

        // Create and save example medical histories
        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(john)
                .visitDate(LocalDate.of(2025, 1, 10))
                .diagnosis("Flu")
                .treatment("Rest and hydration")
                .notes("Patient advised to take it easy.")
                .prescribedMedications("Paracetamol")
                .doctorName("Dr. Smith")
                .visitType(VisitType.CHECKUP)
                .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                        .patient(john)
                        .visitDate(LocalDate.of(2025, 1, 15))
                        .diagnosis("Headache")
                        .treatment("Migraine treatment")
                        .notes("Follow up in 2 weeks.")
                        .prescribedMedications("Aspirin")
                        .doctorName("Dr. Johnson")
                        .visitType(VisitType.CHECKUP)
                        .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                        .patient(john)
                        .visitDate(LocalDate.of(2025, 1, 20))
                        .diagnosis("Cough")
                        .treatment("Cough medicine")
                        .notes("Follow up in 2 weeks.")
                        .prescribedMedications("Aspirin")
                        .doctorName("Dr. Williams")
                        .visitType(VisitType.CHECKUP)
                        .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(jane)
                .visitDate(LocalDate.of(2025, 2, 15))
                .diagnosis("Allergy")
                .treatment("Antihistamines")
                .notes("Monitor symptoms.")
                .prescribedMedications("Cetirizine")
                .doctorName("Dr. Brown")
                .visitType(VisitType.EMERGENCY)
                .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                        .patient(jane)
                        .visitDate(LocalDate.of(2025, 2, 20))
                        .diagnosis("Back Pain")
                        .treatment("Physical therapy")
                        .notes("Follow up in 2 weeks.")
                        .prescribedMedications("Ibuprofen")
                        .doctorName("Dr. Davis")
                        .visitType(VisitType.CHECKUP)
                        .build());


        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(mary)
                .visitDate(LocalDate.of(2025, 3, 20))
                .diagnosis("Routine Checkup")
                .treatment("Annual checkup")
                .notes("All tests normal.")
                .prescribedMedications("None")
                .doctorName("Dr. Green")
                .visitType(VisitType.VACCINATION)
                .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(bob)
                .visitDate(LocalDate.of(2025, 4, 25))
                .diagnosis("Back Pain")
                .treatment("Physical therapy")
                .notes("Follow up in 2 weeks.")
                .prescribedMedications("Ibuprofen")
                .doctorName("Dr. Black")
                .visitType(VisitType.CHECKUP)
                .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(bob)
                .visitDate(LocalDate.of(2025, 5, 1))
                .diagnosis("Flu")
                .treatment("Rest and hydration")
                .notes("Patient advised to take it easy.")
                .prescribedMedications("Paracetamol")
                .doctorName("Dr. White")
                .visitType(VisitType.CHECKUP)
                .build());

        medicalHistoryRepository.save(MedicalHistory.builder()
                .patient(bob)
                .visitDate(LocalDate.of(2025, 5, 10))
                .diagnosis("Headache")
                .treatment("Migraine treatment")
                .notes("Follow up in 2 weeks.")
                .prescribedMedications("Aspirin")
                .doctorName("Dr. White")
                .build());
    }
}
