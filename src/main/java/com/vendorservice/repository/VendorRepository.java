package com.vendorservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vendorservice.entity.Vendor;
import com.vendorservice.service.VendorNativeResult;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

	@EntityGraph(attributePaths = "vendorServiceArea")
	@Query("SELECT v FROM Vendor v LEFT JOIN FETCH v.vendorServiceArea WHERE v.vendorId = :vendorId")
	Vendor findByVendorId(@Param("vendorId") Long vendorId);

	@Query(value = """
	        SELECT v.vendorId AS vendorId, v.vfirstname, v.vlastname, v.vmobile, v.vemail,
	               vs.vserviceid, vs.vzipcode, vs.basepriceperkm AS basePricePerKm,
	               vs.priceperkg AS pricePerKg, vs.avgdeliverytimeindays AS avgDeliveryTimeInDays
	        FROM VENDOR.VENDOR v
	        JOIN VENDOR.vendor_service_area vs ON v.vendorId = vs.vendorId
	        WHERE vs.vzipcode = :zipcode
	        """, nativeQuery = true)
	    List<VendorNativeResult> findVendorsByZipcodeNative(@Param("zipcode") String zipcode);
	
}
