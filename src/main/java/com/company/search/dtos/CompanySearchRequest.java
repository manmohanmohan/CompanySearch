package com.company.search.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CompanySearchRequest {

    private String companyName;
    private String companyNumber;
}