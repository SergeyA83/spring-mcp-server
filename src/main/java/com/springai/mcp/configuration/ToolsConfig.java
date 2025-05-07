package com.springai.mcp.configuration;

import com.springai.mcp.service.MedicalHistoryService;
import com.springai.mcp.service.PatientService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ToolsConfig {
    @Bean
    public List<ToolCallback> findTools(PatientService patientService, MedicalHistoryService medicalHistoryService) {
        return List.of(ToolCallbacks.from(patientService, medicalHistoryService));
    }
}
