package com.company.search.entities;

import com.company.search.dtos.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Company {
    @Id
    private String companyNumber;
    private String companyName;
    private String companyType;
    private String companyStatus;
    private String dateOfCreation;
    // Other fields and getters/setters

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Officer> officers;
}
