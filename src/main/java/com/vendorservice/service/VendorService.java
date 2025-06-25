package com.vendorservice.service;

import java.util.List;

import com.vendorservice.domain.VendorResponse;
import com.vendorservice.exceptions.StatusHandler;


public interface VendorService {

	VendorResponse getVendorDetails(Long vendorId, VendorResponse response, StatusHandler statusHandler);

	

}
