package com.vendorservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vendorservice.domain.VendorDTO;
import com.vendorservice.domain.VendorServiceAreaDTO;
import com.vendorservice.entity.Vendor;
import com.vendorservice.entity.VendorServiceArea;

@Mapper(componentModel = "spring")
public interface VendorMapper {

	@Mapping(target = "vendorServiceAreaDTO", source = "vendorServiceArea")
    VendorDTO toDTO(Vendor vendor);

    VendorServiceAreaDTO toDTO(VendorServiceArea entity);

    List<VendorServiceAreaDTO> toDtoList(List<VendorServiceArea> list);
    
	
}
