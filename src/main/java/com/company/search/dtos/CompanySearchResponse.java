package com.company.search.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanySearchResponse {
    private int totalResults;
    private List<CompanyDTO> items;

}
