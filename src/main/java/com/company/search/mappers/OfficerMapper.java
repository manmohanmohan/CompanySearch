package com.company.search.mappers;

import com.company.search.dtos.AddressDTO;
import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Address;
import com.company.search.entities.Officer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OfficerMapper {

    OfficerMapper INSTANCE = Mappers.getMapper(OfficerMapper.class);

    @Mappings({
            @Mapping(target = "address", source = "address")
    })
    Officer officerDTOToOfficer(OfficerDTO officerDTO);

    @Mappings({
            @Mapping(target = "address", source = "address")
    })
    OfficerDTO officerToOfficerDTO(Officer officer);

    @Mappings({})
    List<OfficerDTO> officerListToOfficerDTOList(List<Officer> officers);

    List<Officer> officerDTOListToOfficerList(List<OfficerDTO> officerDTOs);

    default Address addressDTOToAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();
        address.setLocality(addressDTO.getLocality());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setPremises(addressDTO.getPremises());
        address.setAddressLine1(addressDTO.getAddressLine1());
        // Add additional mappings if needed
        return address;
    }

    default AddressDTO addressToAddressDTO(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setLocality(address.getLocality());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setPremises(address.getPremises());
        addressDTO.setAddressLine1(address.getAddressLine1());
        // Add additional mappings if needed
        return addressDTO;
    }

    default List<AddressDTO> addressListToAddressDTOList(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }

        return addresses.stream()
                .map(this::addressToAddressDTO)
                .collect(Collectors.toList());
    }

    default List<Address> addressDTOListToAddressList(List<AddressDTO> addressDTOs) {
        if (addressDTOs == null) {
            return null;
        }

        return addressDTOs.stream()
                .map(this::addressDTOToAddress)
                .collect(Collectors.toList());
    }
}
