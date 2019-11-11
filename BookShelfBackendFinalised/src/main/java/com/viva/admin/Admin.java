package com.viva.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

	@Id
	private int    adminId;
	private String adminName;
	private String adminPassword;
	private Date   adminDob;
	private String adminAddress;
	private String adminEmail;
	
	public Admin() {
		super();
	}

	public Admin(int adminId, String adminName, String adminPassword, Date adminDob, String adminAddress,
			String adminEmail) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminPassword = adminPassword;
		this.adminDob = adminDob;
		this.adminAddress = adminAddress;
		this.adminEmail = adminEmail;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public Date getAdminDob() {
		return adminDob;
	}

	public void setAdminDob(Date adminDob) {
		this.adminDob = adminDob;
	}

	public String getAdminAddress() {
		return adminAddress;
	}

	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	
	
	
}
