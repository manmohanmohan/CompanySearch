package com.company.search.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @JsonProperty("locality")
    private String locality;

    @JsonProperty("postal_code")
    private String postalCode;
    private String premises;

    private String addressLine1;
    // Other fields and getters/setters
}