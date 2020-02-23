package com.jdc.accounting.model;

import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.utils.ValidationUtils;

public class AuthManager {

	private UserDetailsService userService;
	
	public AuthManager(UserDetailsService userService) {
		this.userService = userService;
	}
	
	public Employee login(String code, String pass) {
		
		ValidationUtils.notEmptyStringInput(code, "Login ID");
		
		ValidationUtils.notEmptyStringInput(pass, "Password");
		
		Employee emp = userService.findByCode(code);
		
		if(null == emp) {
			throw new BalanceException("Please check your Login Id!");
		}
		
		if(!pass.equals(emp.getPassword())) {
			throw new BalanceException("Please check your Password!");
		}
		
		return emp;
	}
}
