package com.jdc.accounting.context;

import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;

public class ApplicationContext {

	private static Employee loginUser;

	public static Employee getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(Employee loginUser) {
		ApplicationContext.loginUser = loginUser;
	}

	public static boolean isAdamin() {
		return loginUser.getRole() == Role.Admin;
	}
}
