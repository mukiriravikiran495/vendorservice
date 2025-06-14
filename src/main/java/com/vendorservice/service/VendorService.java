package com.vendorservice.service;

import java.util.List;

import com.vendorservice.domain.VendorBookingResponseDTO;

public interface VendorService {

	List<VendorBookingResponseDTO> getBookingByVendorId(long vendorId);

}
