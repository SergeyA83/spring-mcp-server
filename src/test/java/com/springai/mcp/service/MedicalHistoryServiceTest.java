package com.springai.mcp.service;

import com.springai.mcp.domain.MedicalHistory;
import com.springai.mcp.domain.Patient;
import com.springai.mcp.domain.VisitType;
import com.springai.mcp.dto.MedicalHistoryDto;
import com.springai.mcp.mapper.MedicalHistoryMapper;
import com.springai.mcp.repository.MedicalHistoryRepository;
import com.springai.mcp.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalHistoryServiceTest {

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicalHistoryMapper medicalHistoryMapper;

    @InjectMocks
    private MedicalHistoryService medicalHistoryService;

    private MedicalHistoryDto medicalHistoryDto;
    private MedicalHistory medicalHistory;

    @BeforeEach
    void setUp() {
        medicalHistoryDto = MedicalHistoryDto.builder()
                .patientId(1L)
                .visitDate(LocalDate.now())
                .diagnosis("Diagnosis")
                .treatment("Treatment")
                .notes("Notes")
                .prescribedMedications("Medications")
                .doctorName("Doctor")
                .visitType(VisitType.CHECKUP)
                .build();
        medicalHistory = new MedicalHistory();
    }

    @Test
    void testFindByPatientId() {
        List<MedicalHistory> medicalHistories = Arrays.asList(new MedicalHistory(), new MedicalHistory());
        when(medicalHistoryRepository.findByPatientId(1L)).thenReturn(medicalHistories);

        List<MedicalHistory> result = medicalHistoryService.findByPatientId(1L);

        assertEquals(medicalHistories, result);
        verify(medicalHistoryRepository, times(1)).findByPatientId(1L);
    }

    @Test
    void testFindByPatientIdAndVisitDateBetween() {
        List<MedicalHistory> medicalHistories = Arrays.asList(new MedicalHistory(), new MedicalHistory());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        when(medicalHistoryRepository.findByPatientIdAndVisitDateBetween(1L,
                startDate,
                endDate)).thenReturn(medicalHistories);

        List<MedicalHistory> result
                = medicalHistoryService.findByPatientIdAndVisitDateBetween(1L,
                startDate,
                endDate);

        assertEquals(medicalHistories, result);
        verify(medicalHistoryRepository, times(1))
                .findByPatientIdAndVisitDateBetween(1L, startDate, endDate);
    }

    @Test
    void testAdd() {
        LocalDate visitDate = LocalDate.now();

        when(medicalHistoryRepository.findByPatientIdAndVisitDate(1L, visitDate))
                .thenReturn(Optional.empty());
        when(patientRepository.findById(1L)).thenReturn(Optional.of(Patient.builder().build()));
        when(medicalHistoryMapper.toMedicalHistory(medicalHistoryDto))
                .thenReturn(medicalHistory);
        when(medicalHistoryRepository.save(medicalHistory))
                .thenReturn(medicalHistory);

        MedicalHistory result = medicalHistoryService.add(medicalHistoryDto);

        assertEquals(medicalHistory, result);
        verify(medicalHistoryRepository, times(1))
                .findByPatientIdAndVisitDate(1L, visitDate);
        verify(patientRepository, times(1)).findById(1L);
        verify(medicalHistoryMapper, times(1)).toMedicalHistory(medicalHistoryDto);
        verify(medicalHistoryRepository, times(1)).save(medicalHistory);
    }

    @Test
    void testAddWithExistingMedicalHistory() {
        LocalDate visitDate = LocalDate.now();

        when(medicalHistoryRepository.findByPatientIdAndVisitDate(1L, visitDate))
                .thenReturn(Optional.of(medicalHistory));

        assertThrows(IllegalStateException.class, () -> medicalHistoryService.add(medicalHistoryDto));
        verify(medicalHistoryRepository, times(1))
                .findByPatientIdAndVisitDate(1L, visitDate);
    }

    @Test
    void testUpdate() {
        LocalDate visitDate = LocalDate.now();

        when(medicalHistoryRepository.findByPatientIdAndVisitDate(1L, visitDate))
                .thenReturn(Optional.of(medicalHistory));
        when(medicalHistoryRepository.save(medicalHistory)).thenReturn(medicalHistory);

        MedicalHistory result = medicalHistoryService.update(medicalHistoryDto);

        assertEquals(medicalHistory, result);
        verify(medicalHistoryRepository, times(1))
                .findByPatientIdAndVisitDate(1L, visitDate);
        verify(medicalHistoryRepository, times(1)).save(medicalHistory);
    }

    @Test
    void testUpdateWithNonExistingMedicalHistory() {
        LocalDate visitDate = LocalDate.now();

        when(medicalHistoryRepository.findByPatientIdAndVisitDate(1L, visitDate))
                .thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> medicalHistoryService.update(medicalHistoryDto));
        verify(medicalHistoryRepository, times(1))
                .findByPatientIdAndVisitDate(1L, visitDate);
    }
}

