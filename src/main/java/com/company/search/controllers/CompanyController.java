package com.company.search.controllers;

import com.company.search.dtos.CompanyDTO;
import com.company.search.dtos.CompanySearchRequest;
import com.company.search.dtos.CompanySearchResponse;
import com.company.search.services.CompanyService;
import com.company.search.validators.CompanyDTOValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDTOValidator companyDTOValidator;

    @PostMapping("/companies/save")
    public ResponseEntity<String> saveCompany(@Valid @RequestBody List<CompanyDTO> companyDTOs, BindingResult bindingResult) {
        if(CollectionUtils.isEmpty(companyDTOs)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valid request body required");
        }
        // Validate each CompanyDTO using custom validator
        for (int i = 0; i < companyDTOs.size(); i++) {
            CompanyDTO companyDTO = companyDTOs.get(i);
            Errors errors = new BeanPropertyBindingResult(companyDTO, "companyDTO[" + i + "]");
            companyDTOValidator.validate(companyDTO, errors);
            if (errors.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors for CompanyDTO[" + i + "]: " + errors.getAllErrors());
            }
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + bindingResult.getAllErrors());
        }

        companyService.saveCompany(companyDTOs);
        return ResponseEntity.ok("Company saved successfully");
    }

    @GetMapping("/company/{companyNumber}")
    public ResponseEntity<?> getCompany(@PathVariable String companyNumber) {
        CompanyDTO companyDTO = companyService.findByCompanyNumber(companyNumber);
        if (companyDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
        }
        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping("company/search")
    public ResponseEntity<?> searchCompany(@Valid @RequestBody CompanySearchRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + bindingResult.getAllErrors());
        }
        CompanySearchResponse response = companyService.searchCompany(request);
        return ResponseEntity.ok(response);
    }


}