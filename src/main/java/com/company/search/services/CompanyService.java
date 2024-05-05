package com.company.search.services;

import com.company.search.dtos.CompanyDTO;
import com.company.search.dtos.CompanySearchRequest;
import com.company.search.dtos.CompanySearchResponse;
import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Address;
import com.company.search.entities.Company;
import com.company.search.entities.Officer;
import com.company.search.exceptions.CompanyNameNotFoundException;
import com.company.search.exceptions.CompanyNotFoundException;
import com.company.search.exceptions.CompanyNumberOrNameRequiredException;
import com.company.search.mappers.CompanyMapper;
import com.company.search.repositories.AddressRepository;
import com.company.search.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {
//    @Autowired
//    private RestTemplate restTemplate;

    private final CompanyMapper companyMapper;
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;

    private final OfficerService officerService;
    // Implement methods for searching companies, retrieving officers, etc.

    @Transactional
    public void saveCompany(List<CompanyDTO> companyDTOs) {
        List<Company> companies = companyMapper.companyDTOListToCompanyList(companyDTOs);
        companies.forEach(company -> {
            // Save Address
            if (company.getAddress() != null) {
                Address savedAddress = addressRepository.save(company.getAddress());
                company.setAddress(savedAddress);
            }

            // Save Officers
            if (!CollectionUtils.isEmpty(company.getOfficers())) {
                company.getOfficers().forEach(officer -> officer.setCompany(company));
                officerService.saveOfficers(company.getOfficers());
                company.setOfficers(company.getOfficers());
            }
        });

        companyRepository.saveAll(companies);
    }

    public CompanyDTO findByCompanyNumber(String companyNumber) {
        Company company = companyRepository.findByCompanyNumberAndCompanyStatus(companyNumber, "Active")
                .orElseThrow(
                        () -> new CompanyNotFoundException(companyNumber));
        return companyMapper.companyToCompanyDTO(company);
    }

    public List<CompanyDTO> findByCompanyName(String companyName) {
        List<Company> companies = companyRepository.findByCompanyNameAndCompanyStatus(companyName, "Active").orElseThrow(
                () -> new CompanyNameNotFoundException(companyName)


        );
        return companyMapper.companyListToCompanyDTOList(companies);
    }

    public CompanySearchResponse searchCompany(CompanySearchRequest request) {
        CompanyDTO companyDTO;
        CompanySearchResponse companySearchResponse = new CompanySearchResponse();
        if (request.getCompanyNumber() != null) {
            companyDTO = findByCompanyNumber(request.getCompanyNumber());

            List<OfficerDTO> officers = officerService.getOfficers(request.getCompanyNumber());
            companyDTO.setOfficers(officers);
            if (companyDTO != null) {
                companySearchResponse.setTotalResults(1);

                companySearchResponse.setItems(List.of(companyDTO));
            }
        } else if (request.getCompanyName() != null) {
            List<CompanyDTO> companyDTOs = findByCompanyName(request.getCompanyName());
            if (CollectionUtils.isEmpty(companyDTOs)) {
                throw new CompanyNameNotFoundException(request.getCompanyName());
            } else {
                companySearchResponse.setTotalResults(companyDTOs.size());
                companySearchResponse.setItems(companyDTOs);
            }
        } else {
            throw new CompanyNumberOrNameRequiredException();
        }

        return companySearchResponse;
    }
}
