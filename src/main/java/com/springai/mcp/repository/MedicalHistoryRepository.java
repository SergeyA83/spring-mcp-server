package com.springai.mcp.repository;

import com.springai.mcp.domain.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPatientId(Long patientId);

    List<MedicalHistory> findByPatientIdAndVisitDateBetween(Long patientId, LocalDate startDate, LocalDate endDate);

    Optional<MedicalHistory> findByPatientIdAndVisitDate(Long patientId, LocalDate visitDate);
}
