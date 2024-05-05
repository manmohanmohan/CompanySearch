package com.company.search.controllers;


import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Officer;
import com.company.search.services.OfficerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OfficerControllerTest {

    @Mock
    private OfficerService officerService;

    @InjectMocks
    private OfficerController officerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(officerController).build();
    }

    @Test
    public void testGetOfficer() throws Exception {
        // Mock data
        Officer mockOfficer = new Officer();
        mockOfficer.setId(1L);
        mockOfficer.setName("John Doe");

        // Mock behavior
        when(officerService.findById(1L)).thenReturn(mockOfficer);

        // Perform request and validate response
        mockMvc.perform(get("/officer/{officerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));

        // Verify service method is called
        verify(officerService, times(1)).findById(1L);
    }

    @Test
    public void testSaveOfficer() throws Exception {
        // Mock data
        OfficerDTO officerDTO = new OfficerDTO();
        officerDTO.setName("John Doe");
        List<OfficerDTO> officerDTOS = new ArrayList<>();
        officerDTOS.add(officerDTO);

        // Mock behavior
        //doNothing().when(officerService).saveOfficer(Collections.singletonList(officerDTO));

        // Perform request and validate response
        mockMvc.perform(post("/officer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\":\"John Doe\"}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("Company saved successfully"));
    }
}
