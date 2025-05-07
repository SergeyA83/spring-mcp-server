package com.springai.mcp.dto;

import com.springai.mcp.domain.VisitType;

import java.time.LocalDate;

public record MedicalHistoryDto(LocalDate visitDate,
                                String diagnosis,
                                String treatment,
                                String notes,
                                String prescribedMedications,
                                String doctorName,
                                VisitType visitType) {
}
