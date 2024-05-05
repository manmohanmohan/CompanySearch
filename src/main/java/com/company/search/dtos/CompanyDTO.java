package com.company.search.dtos;


import com.company.search.entities.Officer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class CompanyDTO {

    @NotBlank(message = "Company number is required")
    @JsonProperty("company_number")
    private String companyNumber;

    private String companyType;

    @NotBlank(message = "Company name is required")
    @JsonProperty("title")
    private String companyName;

    @JsonProperty("company_status")
    private String companyStatus;

    @JsonProperty("date_of_creation")
    private String dateOfCreation;

    private AddressDTO address;

    private List<OfficerDTO> officers;
}
