package com.company.search.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
