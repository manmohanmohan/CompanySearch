package com.company.search.exceptions;

public class CompanyNameNotFoundException extends RuntimeException{

    public CompanyNameNotFoundException(String companyName) {
        super("Company with company name " + companyName + " not found");
    }

}
