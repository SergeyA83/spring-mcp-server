package com.springai.mcp.dto;

import com.springai.mcp.domain.VisitType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MedicalHistoryDto(Long patientId,
                                LocalDate visitDate,
                                String diagnosis,
                                String treatment,
                                String notes,
                                String prescribedMedications,
                                String doctorName,
                                VisitType visitType) {
}
