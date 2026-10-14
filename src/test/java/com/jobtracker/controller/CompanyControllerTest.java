package com.jobtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobtracker.dto.CompanyDTO;
import com.jobtracker.model.Company;
import com.jobtracker.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Company sampleCompany;

    @BeforeEach
    void setUp() {
        sampleCompany = Company.builder()
                .id(1L)
                .name("Google")
                .industry("Technology")
                .website("https://google.com")
                .location("Mountain View, CA")
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/companies - Should return all companies")
    void getAllCompanies() throws Exception {
        when(companyService.getAllCompanies(any(), any()))
                .thenReturn(List.of(sampleCompany));

        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Google"));
    }

    @Test
    @DisplayName("GET /api/v1/companies/{id} - Should return company by ID")
    void getCompanyById() throws Exception {
        when(companyService.getCompanyById(1L)).thenReturn(sampleCompany);

        mockMvc.perform(get("/api/v1/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Google"));
    }

    @Test
    @DisplayName("POST /api/v1/companies - Should create a company")
    void createCompany() throws Exception {
        CompanyDTO dto = CompanyDTO.builder()
                .name("Google")
                .industry("Technology")
                .build();

        when(companyService.createCompany(any(CompanyDTO.class))).thenReturn(sampleCompany);

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Google"));
    }

    @Test
    @DisplayName("POST /api/v1/companies - Should return 400 for invalid input")
    void createCompanyValidationError() throws Exception {
        CompanyDTO dto = CompanyDTO.builder().build(); // missing name

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
