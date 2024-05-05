package com.company.search.controllers;

import com.company.search.dtos.CompanyDTO;
import com.company.search.dtos.CompanySearchRequest;
import com.company.search.dtos.CompanySearchResponse;
import com.company.search.exceptions.CompanyNotFoundException;
import com.company.search.exceptions.CompanyNumberOrNameRequiredException;
import com.company.search.exceptions.GlobalExceptionHandler;
import com.company.search.services.CompanyService;
import com.company.search.validators.CompanyDTOValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyDTOValidator companyDTOValidator;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    @DisplayName("Case1: Success Get Company by number")
    public void testGetCompany_Case1() throws Exception {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .companyNumber("06500244")
                .companyName("BBC LIMITED")
                .build();

        when(companyService.findByCompanyNumber(Mockito.anyString())).thenReturn(companyDTO);

        mockMvc.perform(get("/company/06500244"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company_number", Matchers.is("06500244")))
                .andExpect(jsonPath("$.title", Matchers.is("BBC LIMITED")));
    }

    @Test
    @DisplayName("Case2: Fail Get Company by number not found")
    public void testGetCompany_Case2() throws Exception {
        when(companyService.findByCompanyNumber(Mockito.anyString()))
                .thenThrow(new CompanyNotFoundException("06500244"));

        mockMvc.perform(get("/company/06500244"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Case3: Fail if internal server error occurs")
    public void testGetCompany_Case3() throws Exception {
        when(companyService.findByCompanyNumber(Mockito.anyString()))
                .thenThrow(new RuntimeException(""));

        mockMvc.perform(get("/company/06500244"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Case1: Search company valid request")
    public void testSearchCompany_ValidRequest() throws Exception {
        CompanySearchRequest request = new CompanySearchRequest();
        CompanySearchResponse mockResponse = new CompanySearchResponse();
        CompanyDTO companyDTO = CompanyDTO.builder()
                .companyNumber("06500244")
                .companyName("BBC LIMITED")
                .build();
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        companyDTOS.add(companyDTO);
        mockResponse.setItems(companyDTOS);
        when(companyService.searchCompany(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/company/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].title").value("BBC LIMITED"))
                .andExpect(jsonPath("$.items[0].company_number").value("06500244")); // Assert response fields as needed
        verify(companyService, times(1)).searchCompany(any());
    }

    @Test
    public void testSearchCompany_InvalidRequest() throws Exception {
        CompanySearchRequest request = new CompanySearchRequest();
        // Set up mock response
        CompanySearchResponse mockResponse = new CompanySearchResponse();
        // Mocking the service method to return the mock response
        when(companyService.searchCompany(any())).thenThrow(CompanyNumberOrNameRequiredException.class);

        // Sending empty request body to trigger validation error
        mockMvc.perform(post("/company/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    // Utility method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveCompany_ValidRequest() throws Exception {
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        // Add some valid CompanyDTOs to the list

        companyDTOs.add(CompanyDTO.builder()
                .companyName("abcd")
                .companyNumber("12345")
                .companyType("tld")
                .build());

        // Mocking the service method to return success
        //  when(companyService.saveCompany(any())).thenReturn(true);

        mockMvc.perform(post("/companies/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(companyDTOs)))
                .andExpect(status().isOk())
                .andExpect(content().string("Company saved successfully"));

        // Verify that saveCompany method of companyService is called once
        verify(companyService, times(1)).saveCompany(any());
    }

    @Test
    public void testSaveCompany_EmptyRequestBody() throws Exception {
        mockMvc.perform(post("/companies/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Valid request body required"));

        // Verify that saveCompany method of companyService is never called
        verify(companyService, never()).saveCompany(any());
    }


}