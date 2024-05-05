package com.company.search.dtos;

import com.company.search.entities.Address;
import com.company.search.entities.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OfficerDTO {

    private String name;

    @JsonProperty("officer_role")
    private String officerRole;

    @JsonProperty("appointed_on")
    private String appointedOn;

    private AddressDTO address;

}
