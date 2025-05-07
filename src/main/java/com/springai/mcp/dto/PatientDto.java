package com.springai.mcp.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientDto(String firstName, String lastName, LocalDate dateOfBirth) {
}
