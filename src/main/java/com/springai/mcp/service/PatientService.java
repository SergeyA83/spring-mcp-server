package com.springai.mcp.service;

import com.springai.mcp.dto.PatientDto;
import com.springai.mcp.domain.Patient;
import com.springai.mcp.mapper.PatientMapper;
import com.springai.mcp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Tool(name = "Find_All_Patients", description = "Find a complete list of Patients")
    public List<Patient> findAll() {
        log.info("Finding all Patients");
        return patientRepository.findAll();
    }

    @Tool(name = "Find_Patient_by_Fist_Name", description = "Find Patient by given Fist Name")
    public List<Patient> findByFirstName(String firstName){
        log.info("Finding Patient by First Name {}", firstName);
        return patientRepository.findByFirstName(firstName);
    }

    @Tool(name = "Find_Patient_by_Last_Name", description = "Find Patient by given Last Name")
    public List<Patient> findByLastName(String lastName){
        log.info("Finding Patient by Last Name {}", lastName);
        return patientRepository.findByLastName(lastName);
    }

    @Tool(name = "Find_Patient_by_Fist_And_Last_Name", description = "Find Patient by given Fist And Last Name")
    public Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName){
        log.info("Finding Patient by First Name {} and Last Name {}", firstName, lastName);
        return patientRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Tool(name = "Adding_or_changing_Patient",
            description = "Adding or changing Patient information by First Name and Last Name and Date of Birth")
    public Patient save(PatientDto patientDto) {
        log.info("Creating or updating Patient {}", patientDto);
        return patientRepository.save(patientMapper.toPatient(patientDto));
    }
}
