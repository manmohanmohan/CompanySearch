package com.company.search.validators;

import com.company.search.dtos.CompanyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CompanyDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CompanyDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompanyDTO companyDTO = (CompanyDTO) target;

        if (companyDTO.getCompanyNumber() == null || companyDTO.getCompanyNumber().isEmpty()) {
            errors.rejectValue("companyNumber", "required", "Required value for field company number");
        }

        if (companyDTO.getCompanyName() == null || companyDTO.getCompanyName().isEmpty()) {
            errors.rejectValue("companyName", "required", "Required value for field company name");
        }
    }
}