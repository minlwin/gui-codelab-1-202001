package com.jdc.accounting.model;

import com.jdc.accounting.model.entity.Employee;

public interface UserDetailsService {

	Employee findByCode(String code);
}
