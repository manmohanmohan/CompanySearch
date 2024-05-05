package com.company.search.exceptions;

public class CompanyNumberOrNameRequiredException extends RuntimeException{

    public CompanyNumberOrNameRequiredException() {
        super("Company Number or name is required to search");
    }
}
