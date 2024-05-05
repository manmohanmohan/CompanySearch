package com.company.search.services;


import com.company.search.dtos.AddressDTO;
import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Officer;
import com.company.search.mappers.OfficerMapper;
import com.company.search.repositories.OfficerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OfficerServiceTest {

    @Mock
    private OfficerRepository officerRepository;

    @Mock
    private OfficerMapper officerMapper;

    @InjectMocks
    private OfficerService officerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindById() {
        Long id = 1L;
        Officer mockOfficer = new Officer();
        mockOfficer.setId(id);
        when(officerRepository.findById(id)).thenReturn(Optional.of(mockOfficer));

        Officer result = officerService.findById(id);

        Assert.notNull(result, "Officer should not be null");
        Assert.isTrue(result.getId().equals(id), "Returned officer should have the same id as the one searched for");
    }

    @Test
    public void testSaveOfficer() {
        List<OfficerDTO> officerDTOs = new ArrayList<>();
        OfficerDTO officerDTO = new OfficerDTO(); // Add necessary data to officerDTO
        officerDTOs.add(officerDTO);
        List<Officer> officers = new ArrayList<>();
        when(officerMapper.officerDTOListToOfficerList(officerDTOs)).thenReturn(officers);

        officerService.saveOfficer(officerDTOs);

        verify(officerRepository, times(1)).saveAll(officers);
    }

    @Test
    public void testGetOfficers() {
        String companyNumber = "123456789";
        List<Officer> mockOfficers = new ArrayList<>();
        when(officerRepository.findAllByCompanyCompanyNumber(companyNumber)).thenReturn(mockOfficers);
        List<OfficerDTO> mockOfficerDTOs = new ArrayList<>();
//        when(officerMapper.officerListToOfficerDTOList(mockOfficers)).thenReturn(mockOfficerDTOs);

        List<OfficerDTO> result = officerService.getOfficers(companyNumber);

        Assert.isTrue(result.size() == mockOfficerDTOs.size(), "Number of officers returned should match mock data");
    }

    @Test
    public void testSaveOfficers() {
        List<Officer> officers = new ArrayList<>();
        OfficerDTO officerDTO = new OfficerDTO();
        officerDTO.setName("Ravi");
        officerDTO.setAppointedOn("2024-01-01");
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine1("abc road");
        addressDTO.setLocality("local1");
        addressDTO.setPostalCode("asfaf");
        addressDTO.setPremises("abc house");
        officerDTO.setAddress(addressDTO);
        // Add necessary data to officers list

        officerService.saveOfficers(officers);

        verify(officerRepository, times(1)).saveAll(officers);
    }
}