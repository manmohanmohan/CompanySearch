package com.company.search.mappers;

import com.company.search.dtos.CompanyDTO;
import com.company.search.entities.Company;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompanyMapper {

    @Mappings({
//            @Mapping(target = "companyNumber", source = "companyNumber"),
//            @Mapping(target = "companyName", source = "companyName"),
//            @Mapping(target = "companyType", source = "companyType"),
//            @Mapping(target = "companyStatus", source = "companyStatus"),
//            @Mapping(target = "dateOfCreation", source = "dateOfCreation"),
//            @Mapping(target = "officers", source = "officers")
            // Add additional mappings for nested objects like AddressDTO if needed
    })
    Company companyDTOToCompany(CompanyDTO companyDTO);

    @Mappings({
            @Mapping(target = "companyNumber", source = "companyNumber"),
            @Mapping(target = "companyName", source = "companyName"),
            // Add additional mappings if needed
    })
    CompanyDTO companyToCompanyDTO(Company company);

    @Mappings({
            @Mapping(target = "companyNumber", source = "companyNumber"),
            @Mapping(target = "companyName", source = "companyName"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "officers", source = "officers")
            // Add additional mappings if needed
    })
    List<CompanyDTO> companyListToCompanyDTOList(List<Company> companies);

    @Mappings({
            @Mapping(target = "companyNumber", source = "companyNumber"),
            @Mapping(target = "companyName", source = "companyName"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "officers", source = "officers")
            // Add additional mappings if needed
    })
    List<Company> companyDTOListToCompanyList(List<CompanyDTO> companies);
}