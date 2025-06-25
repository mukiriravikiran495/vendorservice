package com.vendorservice.domain;

import java.util.List;

import com.vendorservice.entity.VendorServiceArea;



public class VendorDTO {

	private Long vendorId;
	private String vfirstname;
	private String vLastname;
	private String vMobile;
	private String vEmail;
	
    private List<VendorServiceAreaDTO> vendorServiceAreaDTO;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVfirstname() {
		return vfirstname;
	}

	public void setVfirstname(String vfirstname) {
		this.vfirstname = vfirstname;
	}

	public String getvLastname() {
		return vLastname;
	}

	public void setvLastname(String vLastname) {
		this.vLastname = vLastname;
	}

	public String getvMobile() {
		return vMobile;
	}

	public void setvMobile(String vMobile) {
		this.vMobile = vMobile;
	}

	public String getvEmail() {
		return vEmail;
	}

	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
	}

	public List<VendorServiceAreaDTO> getVendorServiceAreaDTO() {
		return vendorServiceAreaDTO;
	}

	public void setVendorServiceAreaDTO(List<VendorServiceAreaDTO> vendorServiceAreaDTO) {
		this.vendorServiceAreaDTO = vendorServiceAreaDTO;
	}

    
    
}
