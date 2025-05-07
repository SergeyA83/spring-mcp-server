package com.springai.mcp.repository;

import com.springai.mcp.domain.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstName(String firstName);
    List<Patient> findByLastName(String lastName);
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}