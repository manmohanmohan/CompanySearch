package com.company.search.mappers;

import com.company.search.dtos.AddressDTO;
import com.company.search.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AddressMapper {

    @Mapping(source = "postalCode", target = "premises") // Example mapping, adjust as needed
    Address addressDTOToAddress(AddressDTO addressDTO);
}
