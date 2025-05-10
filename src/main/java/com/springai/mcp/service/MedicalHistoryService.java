package com.springai.mcp.service;

import com.springai.mcp.domain.MedicalHistory;
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

    @Tool(name = "Get_Patient_full_Medical_History_by_Patient_ID",
            description = "Get Patient full Medical History by real Patient ID")
    public List<MedicalHistory> findByPatientId(Long patientId) {
        log.info("Getting Medical History by Patient Id: {}", patientId);
        return medicalHistoryRepository.findByPatientId(patientId);
    }

    @Tool(name = "Get_Patient_Medical_History_by_Patient_ID_and_Date_Range",
            description = "Get Patient Medical History Patient ID and Date Range")
    public List<MedicalHistory> findByPatientIdAndVisitDateBetween(Long patientId,
                                                                   LocalDate startDate,
                                                                   LocalDate endDate) {
        log.info("Getting Medical History by Patient ID: {} between {} and {}", patientId, startDate, endDate);
        return medicalHistoryRepository.findByPatientIdAndVisitDateBetween(patientId, startDate, endDate);
    }

    @Tool(name = "Adding_Patient_new_Medical_History_by_Patient_ID",
            description = "Adding Patient new Medical History record by real Patient ID")
    public MedicalHistory add(MedicalHistoryDto medicalHistoryDto) {
        // Check if the medical history already exists for the same patient and date
        var medicalHistoryExistingOptional =
                medicalHistoryRepository.findByPatientIdAndVisitDate(medicalHistoryDto.patientId(),
                        medicalHistoryDto.visitDate());

        if (medicalHistoryExistingOptional.isPresent()) {
            throw new IllegalStateException("Medical History already exists for the same Patient and Date");
        }

        var patient = patientRepository.findById(medicalHistoryDto.patientId())
                .orElseThrow(() -> new IllegalStateException("Patient not found"));

        var medicalHistory = medicalHistoryMapper.toMedicalHistory(medicalHistoryDto);
        medicalHistory.setPatient(patient);

        log.info("Adding new Medical History {}", medicalHistoryDto);
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Tool(name = "Changing_existing_Patient_Medical_History",
            description = "Changing existing Patient Medical History record by real Patient ID and Visit Date")
    public MedicalHistory update(MedicalHistoryDto medicalHistoryDto) {
        log.info("Updating Medical History {}", medicalHistoryDto);

        // Retrieve the existing medical history for the specified patient and visit date
        return medicalHistoryRepository.findByPatientIdAndVisitDate(medicalHistoryDto.patientId(),
                        medicalHistoryDto.visitDate())
                .map(existingHistory -> {
                    mergeMedicalHistory(medicalHistoryDto, existingHistory);
                    return medicalHistoryRepository.save(existingHistory);
                })
                .orElseThrow(() ->
                        new IllegalStateException("Medical History not found for the given Patient and Visit Date"));
    }

    private void mergeMedicalHistory(MedicalHistoryDto medicalHistoryDto, MedicalHistory existingHistory) {
        existingHistory.setDiagnosis(medicalHistoryDto.diagnosis());
        existingHistory.setTreatment(medicalHistoryDto.treatment());
        existingHistory.setNotes(medicalHistoryDto.notes());
        existingHistory.setPrescribedMedications(medicalHistoryDto.prescribedMedications());
        existingHistory.setDoctorName(medicalHistoryDto.doctorName());
        existingHistory.setVisitType(medicalHistoryDto.visitType());
    }
}
