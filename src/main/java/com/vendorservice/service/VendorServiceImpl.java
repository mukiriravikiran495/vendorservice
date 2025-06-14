package com.vendorservice.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendorservice.domain.VendorBookingResponseDTO;
import com.vendorservice.entity.MyBookings;
import com.vendorservice.mapper.VendorBookingsMapper;
import com.vendorservice.repository.VendorServiceRespository;

@Service("VendorService")
public class VendorServiceImpl implements VendorService{

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private final VendorServiceRespository repository;
	private final VendorBookingsMapper vendorMapper;
	
	@Autowired
	public VendorServiceImpl(VendorServiceRespository repository, VendorBookingsMapper vendorMapper) {
		
		this.repository = repository;
		this.vendorMapper = vendorMapper;
	}

	@Override
	public List<VendorBookingResponseDTO> getBookingByVendorId(long vendorId) {
		logger.info("START : VendorBookings Service ");
		List<MyBookings> bookings = repository.findByVendorDetails_VendorId(vendorId);
		
		logger.info("END : VendorBookings Service : ");
	    return vendorMapper.toVendorBookingDTOs(bookings);
	}

}
