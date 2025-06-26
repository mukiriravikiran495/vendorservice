package com.vendorservice.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.vendorservice.constants.AppConstants;
import com.vendorservice.domain.VendorDTO;
import com.vendorservice.domain.VendorEstimateRequest;
import com.vendorservice.domain.VendorEstimateResponse;
import com.vendorservice.domain.VendorResponse;
import com.vendorservice.domain.VendorServiceAreaDTO;
import com.vendorservice.entity.Vendor;
import com.vendorservice.exceptions.InvalidRequestException;
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

	@Override
	public VendorEstimateResponse getvendorEstimates(VendorEstimateRequest request,
			VendorEstimateResponse vendorEstimatesResponse, StatusHandler statusHandler) {
		logger.info("Start : Get Estimates Vendors : service : " + request);
		VendorEstimateResponse response = new VendorEstimateResponse();
		try {
			if( null == request.getPickupLatitude() || null == request.getPickupLongitude() || null == request.getDropLatitude() || null == request.getDropLongitude()) {
				throw new InvalidRequestException(AppConstants.INVALID_REQUEST);
			}
			List<VendorNativeResult> flatResult = repository.findVendorsByZipcodeNative(request.getPickupZipcode());
			Optional.ofNullable(flatResult).orElseThrow(() ->  new RuntimeException(AppConstants.VENDROR_SERVICES_NOT_AVAILABLE));
			Map<Long, VendorDTO> vendorMap = new LinkedHashMap<>();

			for (VendorNativeResult row : flatResult) {
				VendorDTO vendorDTO = vendorMap.computeIfAbsent(row.getVendorId(), id -> {
					VendorDTO dto = new VendorDTO();
					dto.setVendorId(row.getVendorId());
					dto.setVfirstname(row.getVfirstname());
					dto.setvLastname(row.getVlastname());
					dto.setvMobile(row.getVmobile());
					dto.setvEmail(row.getVemail());
					dto.setVendorServiceAreaDTO(new ArrayList<>());
					return dto;
				});

				VendorServiceAreaDTO areaDTO = new VendorServiceAreaDTO();
				areaDTO.setvServiceId(row.getVserviceId());
				areaDTO.setvZipcode(row.getVzipcode());
				areaDTO.setBasePricePerKm(row.getBasePricePerKm());
				areaDTO.setPricePerKg(row.getPricePerKg());
				areaDTO.setAvgDeliveryTimeInDays(row.getAvgDeliveryTimeInDays());
				int weight = 0;
				for (int i = 0; i < request.getSelectedItems().size(); i++) {
					weight = weight + request.getSelectedItems().get(i).getWeight();
				}
				int estimatedPrice = weight * row.getBasePricePerKm() + row.getPricePerKg() * 10;
				areaDTO.setEstimatedPrice(estimatedPrice);

				areaDTO.setEstimatedDeliveryDate(null);
				vendorDTO.getVendorServiceAreaDTO().add(areaDTO);
			}

			
			response.setBookingDate(LocalDateTime.now());
			response.setScheduledDate(request.getScheduledDate());
			response.setServicetype(request.getServiceType());

			response.setPickupAddress(request.getPickupAddress());
			response.setPickupLatitude(request.getPickupLatitude());
			response.setPickupLongitude(request.getPickupLongitude());
			response.setPickupZipcode(request.getPickupZipcode());
			response.setDropZipcode(request.getDropZipcode());
			response.setDropAddress(request.getDropAddress());
			response.setDropLatitude(request.getDropLatitude());
			response.setDropLongitude(request.getDropLongitude());

			response.setVendorDTO(new ArrayList<>(vendorMap.values()));

			StatusHandler status = new StatusHandler();
			status.setStatusCode("200");
			status.setMessage(AppConstants.SUCCESS);
			response.setStatusHandler(status);

			
		}catch(Exception ex) {
			
		}
		return response;

		

	}
	

}
