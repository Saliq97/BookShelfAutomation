package com.viva.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viva.admin.Admin;
import com.viva.admin.AdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	
	
	/*
	 * @RequestMapping(value="/validate",method=RequestMethod.POST) public boolean
	 * adminValidation(@RequestBody Admin admin) throws NoSuchAlgorithmException {
	 * 
	 * return adminService.adminValidation(admin);
	 * 
	 * }
	 */
	@RequestMapping("/admin/{id}")
	public Optional<Admin> getAdmin(@PathVariable int id) {
		return adminService.getAdmin(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/admin" )
	public void addAdmin(@RequestBody Admin admin) {
		adminService.addAdmin(admin);
	}

}
