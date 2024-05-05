package com.company.search.services;

import com.company.search.dtos.CompanyDTO;
import com.company.search.dtos.CompanySearchRequest;
import com.company.search.dtos.CompanySearchResponse;
import com.company.search.entities.Company;
import com.company.search.exceptions.CompanyNameNotFoundException;
import com.company.search.exceptions.CompanyNumberOrNameRequiredException;
import com.company.search.mappers.CompanyMapper;
import com.company.search.repositories.CompanyRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCompany() {
        // Mock data
        List<CompanyDTO> companyDTOs = Collections.singletonList(CompanyDTO.builder().build());

        // Mock behavior
        when(companyMapper.companyDTOListToCompanyList(companyDTOs)).thenReturn(Collections.singletonList(new Company()));

        // Test
        assertDoesNotThrow(() -> companyService.saveCompany(companyDTOs));

        // Verify
        verify(companyRepository, times(1)).saveAll(any());
    }

    @Test
    public void testFindByCompanyNumber() {
        // Mock data
        String companyNumber = "123456";
        Company company = new Company();
        company.setCompanyNumber("123456");
        company.setCompanyStatus("Active");
        CompanyDTO expectedCompanyDTO = CompanyDTO.builder()
                .companyNumber("123456")
                .companyStatus("Active")
                .build();

        // Mock behavior
        when(companyRepository.findByCompanyNumberAndCompanyStatus(companyNumber,"Active")).thenReturn(Optional.of(company));
        when(companyMapper.companyToCompanyDTO(company)).thenReturn(expectedCompanyDTO);

        // Test
        CompanyDTO actualCompanyDTO = companyService.findByCompanyNumber(companyNumber);

        // Verify
        assertEquals(expectedCompanyDTO, actualCompanyDTO);
    }

    @Test
    public void testFindByCompanyName() {
        // Mock data
        String companyName = "Company Name";
        CompanyNameNotFoundException exception = new CompanyNameNotFoundException(companyName);

        // Mock behavior
        when(companyRepository.findByCompanyNameAndCompanyStatus(companyName,"Active")).thenReturn(Optional.empty());

        // Test & Verify
        assertThrows(CompanyNameNotFoundException.class, () -> companyService.findByCompanyName(companyName), "Company name not found");

        // Mock behavior
        List<Company> companies = Collections.singletonList(new Company());
        List<CompanyDTO> expectedCompanyDTOs = Collections.singletonList(CompanyDTO.builder().build());
        when(companyRepository.findByCompanyNameAndCompanyStatus(companyName,"Active")).thenReturn(Optional.of(companies));
        when(companyMapper.companyListToCompanyDTOList(companies)).thenReturn(expectedCompanyDTOs);

        // Test
        List<CompanyDTO> actualCompanyDTOs = companyService.findByCompanyName(companyName);

        // Verify
        assertEquals(expectedCompanyDTOs, actualCompanyDTOs);
    }

    @Test
    public void testSearchCompanyByNumber() {
        // Mock data
        String companyNumber = "123456";
        CompanyDTO expectedCompanyDTO = CompanyDTO.builder().build();
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyNumber(companyNumber);
        Company company = new Company();
        company.setCompanyName("ABC Ltd");
        company.setCompanyNumber("123456");
        // Mock behavior
        when( companyRepository.findByCompanyNumberAndCompanyStatus(companyNumber,"Active")).thenReturn(Optional.of(company));
        when(companyMapper.companyToCompanyDTO(any())).thenReturn(expectedCompanyDTO);
      //  when(companyService.findByCompanyNumber(companyNumber)).thenReturn(expectedCompanyDTO);

        // Test
        CompanySearchResponse response = companyService.searchCompany(request);

        // Verify
        assertEquals(1, response.getTotalResults());
        assertEquals(Collections.singletonList(expectedCompanyDTO), response.getItems());
    }

    @Test
    public void testSearchCompanyByName() {
        // Mock data
        String companyName = "ABC Ltd";
        List<CompanyDTO> expectedCompanyDTOs = Collections.singletonList(CompanyDTO
                .builder()
                .companyName("ABC Ltd")
                .companyNumber("1234")
                .build());
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName(companyName);
        List<Company> companies = new ArrayList<>();
        Company company = new Company();
        company.setCompanyNumber("1234");
        company.setCompanyName("ABC Ltd");
        companies.add(company);
        Optional<List<Company>> Companies= Optional.of(companies);

       // Mock behavior
       // when(companyService.findByCompanyName(companyName)).thenReturn(expectedCompanyDTOs);
        when(companyRepository.findByCompanyNameAndCompanyStatus(companyName,"Active")).thenReturn(Companies);
        when(companyMapper.companyListToCompanyDTOList(Companies.get())).thenReturn(expectedCompanyDTOs);

        // Test
        CompanySearchResponse response = companyService.searchCompany(request);

        // Verify
        assertEquals(expectedCompanyDTOs.size(), response.getTotalResults());
        assertEquals(expectedCompanyDTOs, response.getItems());
    }

    @Test
    public void testSearchCompanyWithEmptyRequest() {
        // Mock data
        CompanyNumberOrNameRequiredException exception = new CompanyNumberOrNameRequiredException();

        // Test & Verify
        assertThrows(CompanyNumberOrNameRequiredException.class, () -> companyService.searchCompany(new CompanySearchRequest()));
    }
}