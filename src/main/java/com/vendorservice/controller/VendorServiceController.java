package com.vendorservice.controller;

import java.lang.invoke.MethodHandles;
import java.util.Date;

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
import com.vendorservice.domain.TokenID;
import com.vendorservice.domain.VendorResponse;
import com.vendorservice.exceptions.InvalidRequestException;
import com.vendorservice.exceptions.StatusHandler;
import com.vendorservice.service.VendorService;
import com.vendorservice.utils.JwtUtil;

@RestController
@RequestMapping( path = "/v1/api/vendor")
public class VendorServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final JwtUtil jwtUtil;
	private final VendorService service;

	@Autowired
	public VendorServiceController(VendorService service, JwtUtil jwtUtil) {
		this.service = service;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping( value = "/auth/token")
	public ResponseEntity<TokenID> getToken() {
		String token = jwtUtil.generateTokenId();
		TokenID tokenId = new TokenID();
		tokenId.setToken(token.trim());
		long timestamp = new Date().getTime();
		tokenId.setExpires(timestamp);
		tokenId.setStatus("200");
		tokenId.setResult(AppConstants.TOKEN_GENERATED_SUCCESSFULLY);
		return new ResponseEntity<>(tokenId, HttpStatus.OK);
	}
	
	@GetMapping( value = "/get/{vendorId}" )
	public ResponseEntity<VendorResponse> getVendorDetails(@PathVariable Long vendorId){
		logger.info("Start : get vendor details controller : "+vendorId);
		StatusHandler statusHandler = new StatusHandler();
		VendorResponse response = new VendorResponse();
		try {
			if(null == vendorId) {
				throw new InvalidRequestException(AppConstants.INVALID_REQUEST);
			}
			response = service.getVendorDetails(vendorId, response, statusHandler);
			
			
			statusHandler.setStatusCode("200");
			statusHandler.setErrorMessage(AppConstants.SUCCESS);
			response.setStatusHandler(statusHandler);
		}catch(InvalidRequestException ex) {
			statusHandler.setStatusCode("400");
			statusHandler.setErrorMessage(ex.getMessage());
			response.setStatusHandler(statusHandler);
		}catch(Exception ex) {
			statusHandler.setStatusCode("500");
			statusHandler.setErrorMessage(ex.getMessage());
			response.setStatusHandler(statusHandler);
		}
		
		ResponseEntity<VendorResponse> vendorResponse = new ResponseEntity<VendorResponse>(response, HttpStatus.OK);
		logger.info("End : get vendor details controller : "+vendorId);
		return vendorResponse;
	}

}























