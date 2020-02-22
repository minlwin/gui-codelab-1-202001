package com.jdc.accounting.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;
import com.jdc.accounting.utils.ConnectionManager;
import com.jdc.accounting.utils.StringUtils;

public class EmployeeModel {

	public void save(Employee emp) {
		
		if(isOldData(emp.getCode())) {
			update(emp);
		} else {
			insert(emp);
		}

	}
	
	private void insert(Employee emp) {
		String sql  = "insert into employee values (?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, emp.getCode());
			stmt.setString(2, emp.getName());
			stmt.setString(3, emp.getPhone());
			stmt.setString(4, emp.getEmail());
			stmt.setString(5, emp.getRole().name());
			stmt.setString(6, emp.getPassword());
			stmt.setString(7, emp.getAddress());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void update(Employee emp) {
		String sql  = "update employee set name = ?, phone = ?, email =?, role = ?, address = ? "
				+ "where emp_code = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, emp.getName());
			stmt.setString(2, emp.getPhone());
			stmt.setString(3, emp.getEmail());
			stmt.setString(4, emp.getRole().name());
			stmt.setString(5, emp.getAddress());
			stmt.setString(6, emp.getCode());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private boolean isOldData(String code) {
		
		String sql = "select count(emp_code) from employee where emp_code = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, code);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}

	public List<Employee> search(Role role, String code, String name) {
		
		List<Employee> list = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder("select * from employee where 1 = 1");
		List<Object> params = new ArrayList<>();
		
		if(null != role) {
			sb.append(" and role = ?");
			params.add(role.name());
		}
		
		if(!StringUtils.isEmpty(code)) {
			sb.append(" and emp_code like ?");
			params.add(code.concat("%"));
		}
		
		if(!StringUtils.isEmpty(name)) {
			sb.append(" and name like ?");
			params.add(name.concat("%"));
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Employee emp  = new Employee();
				emp.setCode(rs.getString("emp_code"));
				emp.setName(rs.getString("name"));
				emp.setPhone(rs.getString("phone"));
				emp.setEmail(rs.getString("email"));
				emp.setPassword(rs.getString("password"));
				emp.setAddress(rs.getString("address"));
				emp.setRole(Role.valueOf(rs.getString("role")));
				list.add(emp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
