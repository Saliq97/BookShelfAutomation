package com.viva.admin;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viva.admin.AdminRepository;
import com.viva.admin.Admin;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;

	/*
	 * public boolean AdminValidation(Admin model) throws NoSuchAlgorithmException{
	 * 
	 * Optional<Admin> Admin = Adminrepository.findById(model.getAdminId()); Admin
	 * ad = Admin.get(); HashPwd hasher=new HashPwd(); String
	 * hashed_pwd=hasher.HashingFunction(model.getAdminPassword());
	 * System.out.println(hashed_pwd); if(hashed_pwd.equals(ad.getAdminPassword()))
	 * { return true; } return false; }
	 */

	
	
	public Optional<Admin> getAdmin(int id) {
		return adminRepository.findById(id);
	}

	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}
	
}
