package com.company.search.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String officerRole;
    private LocalDate appointedOn;
    @ManyToOne
    @JoinColumn(name = "CompanyNumber", referencedColumnName = "companyNumber")
    private Company company;

    @OneToOne
    Address address;
}
