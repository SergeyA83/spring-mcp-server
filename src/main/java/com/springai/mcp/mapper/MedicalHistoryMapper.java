package com.springai.mcp.mapper;

import com.springai.mcp.domain.MedicalHistory;
import com.springai.mcp.domain.Patient;
import com.springai.mcp.dto.MedicalHistoryDto;
import com.springai.mcp.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
@Component
public interface MedicalHistoryMapper {
    MedicalHistory toMedicalHistory(MedicalHistoryDto medicalHistoryDto);
}
