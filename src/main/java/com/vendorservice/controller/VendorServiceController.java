package com.vendorservice.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendorservice.constants.AppConstants;
import com.vendorservice.domain.VendorBookingResponseDTO;
import com.vendorservice.exceptions.InvalidRequestException;
import com.vendorservice.exceptions.StatusHandler;
import com.vendorservice.service.VendorService;

@RestController
@RequestMapping( path = "/v1/api/vendor")
public class VendorServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private final VendorService service;

	@Autowired
	public VendorServiceController(VendorService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value = "/bookings/{vendorId}")
	public ResponseEntity<List<VendorBookingResponseDTO>> getBookingByVendorId(@PathVariable Long vendorId) throws InvalidRequestException {
		logger.info("STRAT : Fetch all Vendor Bookings : ");
		
		if(null == vendorId) {
			throw new InvalidRequestException(AppConstants.INVALID_REQUEST);
		}
		
		List<VendorBookingResponseDTO>  vendor = service.getBookingByVendorId(vendorId);
		ResponseEntity<List<VendorBookingResponseDTO>> response = new ResponseEntity<>(vendor, HttpStatus.OK);

		logger.info("END : Fetch all Vendor Bookings : " + vendor);
		return response;
	}

}
