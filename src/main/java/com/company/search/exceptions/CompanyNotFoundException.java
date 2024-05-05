package com.company.search.exceptions;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String companyNumber) {
        super("Company with company number " + companyNumber + " not found");
    }

}
