package com.company.search.services;

import com.company.search.dtos.CompanyDTO;
import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Officer;
import com.company.search.mappers.OfficerMapper;
import com.company.search.repositories.OfficerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OfficerService {
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private OfficerMapper officerMapper;
    // Implement methods for retrieving officers, processing data, etc.
    private final OfficerRepository officerRepository;

    public Officer findById(Long id) {
        return officerRepository.findById(id).orElse(null);
    }

    public void saveOfficer(List<OfficerDTO> officerDTOs) {
        List<Officer> officers = officerMapper.officerDTOListToOfficerList(officerDTOs);
        officerRepository.saveAll(officers);
    }

    public List<OfficerDTO> getOfficers(String companyNumber) {
        List<Officer> officers = officerRepository.findAllByCompanyCompanyNumber(companyNumber);
        List<OfficerDTO> officerDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(officers)) {
            officerDtos = officerMapper.officerListToOfficerDTOList(officers);
        }
        return officerDtos;
    }

    public void saveOfficers(List<Officer> officers){
        officerRepository.saveAll(officers);
    }


}