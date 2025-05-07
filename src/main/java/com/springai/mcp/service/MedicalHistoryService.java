package com.springai.mcp.service;

import com.springai.mcp.domain.MedicalHistory;
import com.springai.mcp.domain.Patient;
import com.springai.mcp.dto.MedicalHistoryDto;
import com.springai.mcp.mapper.MedicalHistoryMapper;
import com.springai.mcp.repository.MedicalHistoryRepository;
import com.springai.mcp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;
    private final MedicalHistoryMapper medicalHistoryMapper;

    @Tool(name = "Get_Patient_full_Medical_History_by_Patient",
                description = "Get Patient full Medical History by Patient First Name and Last Name")
    public List<MedicalHistory> getMedicalHistory(String firstName, String lastName) {
        var patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new IllegalStateException("Patient not found."));

        log.info("Getting Medical History for Patient: {}", patient.getId());
        return medicalHistoryRepository.findByPatientId(patient.getId());
    }

    @Tool(name = "Get_Patient_Medical_History_by_Patient_and_Date_Range",
       description = "Get Patient Medical History Patient First Name and Last Name and Date Range")
    public List<MedicalHistory> getMedicalHistoryByDateRange(String firstName,
                                                             String lastName,
                                                             LocalDate startDate,
                                                             LocalDate endDate) {
        var patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new IllegalStateException("Patient not found."));

        log.info("Getting Medical History for Patient: {} between {} and {}", patient.getId(), startDate, endDate);
        return medicalHistoryRepository.findByPatientIdAndVisitDateBetween(patient.getId(), startDate, endDate);
    }

    @Tool(name = "Adding_Patient_new_Medical_History",
            description = "Adding Patient new Medical History record by Patient First Name and Last Name")
    public MedicalHistory add(String firstName, String lastName, MedicalHistoryDto medicalHistoryDto) {
        var patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new IllegalStateException("Patient not found."));

        // Check if the medical history already exists for the same patient and date
        var medicalHistoryExistingOptional =
                medicalHistoryRepository.findByPatientIdAndVisitDate(patient.getId(),
                        medicalHistoryDto.visitDate());

        if (medicalHistoryExistingOptional.isPresent()) {
            throw new IllegalStateException("Medical history already exists for the same patient and date.");
        }

        return medicalHistoryExistingOptional
                .orElseGet(() -> saveMedicalHistory(medicalHistoryDto, patient));

    }

    private MedicalHistory saveMedicalHistory(MedicalHistoryDto medicalHistoryDto, Patient patient) {
        log.info("Saving Medical History for Patient: {}", medicalHistoryDto);

        var medicalHistory = medicalHistoryMapper.toMedicalHistory(medicalHistoryDto);

        medicalHistory.setPatient(patient);
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Tool(name = "Changing_Patient_existing_Medical_History",
            description = "Changing Patient existing Medical History record by Patient First Name and Last Name")
    public MedicalHistory update(String firstName, String lastName, MedicalHistoryDto medicalHistoryDto) {
        var patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new IllegalStateException("Patient not found."));
        // Check if the medical history already exists for the same patient and date
        var medicalHistoryExistingOptional =
                medicalHistoryRepository.findByPatientIdAndVisitDate(patient.getId(),
                        medicalHistoryDto.visitDate());

        return medicalHistoryExistingOptional
                .map(medicalHistoryExisting -> updateMedicalHistory(medicalHistoryDto, medicalHistoryExisting))
                .orElseThrow(() -> new IllegalStateException("Medical history doesn't exist for the same patient and date."));
    }
    private MedicalHistory updateMedicalHistory(MedicalHistoryDto medicalHistoryDto,
                                                MedicalHistory medicalHistoryExisting) {
        log.info("Updating Medical History for Patient: {}", medicalHistoryDto);
        medicalHistoryExisting.setPrescribedMedications(medicalHistoryDto.prescribedMedications());
        medicalHistoryExisting.setDiagnosis(medicalHistoryDto.diagnosis());
        medicalHistoryExisting.setTreatment(medicalHistoryDto.treatment());
        medicalHistoryExisting.setNotes(medicalHistoryDto.notes());
        medicalHistoryExisting.setVisitType(medicalHistoryDto.visitType());
        medicalHistoryExisting.setDoctorName(medicalHistoryDto.doctorName());
        return medicalHistoryRepository.save(medicalHistoryExisting);
    }
}
