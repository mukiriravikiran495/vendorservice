package com.vendorservice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vendorservice.entity.MyBookings;

@Repository
public interface VendorServiceRespository extends JpaRepository<MyBookings, Long>{
	List<MyBookings> findByVendorDetails_VendorId(Long vendorId);
}
