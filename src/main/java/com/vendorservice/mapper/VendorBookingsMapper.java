package com.vendorservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.vendorservice.domain.VendorBookingResponseDTO;
import com.vendorservice.entity.MyBookings;




@Mapper(componentModel = "spring")
public interface VendorBookingsMapper {

	VendorBookingResponseDTO toVendorBookingDTO(MyBookings booking);
	public List<VendorBookingResponseDTO> toVendorBookingDTOs(List<MyBookings> bookings);
	
}
