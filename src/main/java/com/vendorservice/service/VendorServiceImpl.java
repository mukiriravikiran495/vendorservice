package com.vendorservice.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vendorservice.domain.VendorDTO;
import com.vendorservice.domain.VendorResponse;
import com.vendorservice.domain.VendorServiceAreaDTO;
import com.vendorservice.entity.Vendor;
import com.vendorservice.entity.VendorServiceArea;
import com.vendorservice.exceptions.StatusHandler;
import com.vendorservice.mapper.VendorMapper;
import com.vendorservice.repository.VendorRepository;

@Service("VendorService")
public class VendorServiceImpl implements VendorService{

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final VendorRepository repository;
	private final VendorMapper mapper;
	
	
	public VendorServiceImpl(VendorRepository repository, VendorMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public VendorResponse getVendorDetails(Long vendorId, VendorResponse response, StatusHandler statusHandler) {
		logger.info("Start : get vendor details service : " + vendorId);
	    try {
	        Vendor vDetails = repository.findByVendorId(vendorId);

	        if (vDetails == null) {
	            throw new RuntimeException("VendorId does not exist: " + vendorId);
	        }

	        VendorDTO vendorDTO = new VendorDTO();
	        vendorDTO.setVendorId(vDetails.getVendorId());
	        vendorDTO.setVfirstname(vDetails.getVfirstname());
	        vendorDTO.setvLastname(vDetails.getvLastname());
	        vendorDTO.setvMobile(vDetails.getvMobile());
	        vendorDTO.setvEmail(vDetails.getvEmail());

	        List<VendorServiceAreaDTO> dtoList = mapper.toDtoList(
	            vDetails.getVendorServiceArea() != null ? vDetails.getVendorServiceArea() : new ArrayList<>()
	        );
	        vendorDTO.setVendorServiceAreaDTO(dtoList);

	        response.setVendorDTO(vendorDTO);
	        statusHandler.setStatusCode("200");
	        statusHandler.setErrorMessage("SUCCESS");

	    } catch (RuntimeException ex) {
	        logger.error("RuntimeException in getVendorDetails", ex);
	        statusHandler.setErrorCode("400");
	        statusHandler.setErrorMessage(ex.getMessage());
	    } catch (Exception ex) {
	        logger.error("Exception in getVendorDetails", ex);
	        statusHandler.setErrorCode("500");
	        statusHandler.setErrorMessage("Internal Server Error: " + ex.getMessage());
	    }

	    response.setStatusHandler(statusHandler);
	    logger.info("End : get vendor details service : " + vendorId);
	    return response;
	}
	

}
