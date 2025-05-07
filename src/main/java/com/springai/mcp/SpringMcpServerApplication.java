package com.springai.mcp;

import com.springai.mcp.service.PatientService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMcpServerApplication.class, args);
	}
}
