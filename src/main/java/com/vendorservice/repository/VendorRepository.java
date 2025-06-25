package com.vendorservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vendorservice.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

	@EntityGraph(attributePaths = "vendorServiceArea")
	@Query("SELECT v FROM Vendor v LEFT JOIN FETCH v.vendorServiceArea WHERE v.vendorId = :vendorId")
	Vendor findByVendorId(@Param("vendorId") Long vendorId);

}
