package com.jdc.accounting.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;
import com.jdc.accounting.utils.ConnectionManager;
import com.jdc.accounting.utils.StringUtils;
import com.jdc.accounting.utils.ValidationUtils;

public class EmployeeModel implements UserDetailsService{

	public void save(Employee emp) {
		
		validate(emp);
		
		if(isOldData(emp.getCode())) {
			update(emp);
		} else {
			insert(emp);
		}

	}
	
	private void validate(Employee emp) {
		ValidationUtils.notEmptyStringInput(emp.getCode(), "Employee Code");
		ValidationUtils.notEmptyStringInput(emp.getName(), "Employee Name");
		ValidationUtils.notNullSelect(emp.getRole(), "Role");
		ValidationUtils.notEmptyStringInput(emp.getPassword(), "Password");
		ValidationUtils.notEmptyStringInput(emp.getPhone(), "Phone Number");
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
				return rs.getLong(1) > 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public Employee findByCode(String code) {
		
		String sql = "select * from employee where emp_code = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, code);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getData(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return null;
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
				list.add(getData(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	private Employee getData(ResultSet rs) throws SQLException {
		Employee emp  = new Employee();
		emp.setCode(rs.getString("emp_code"));
		emp.setName(rs.getString("name"));
		emp.setPhone(rs.getString("phone"));
		emp.setEmail(rs.getString("email"));
		emp.setPassword(rs.getString("password"));
		emp.setAddress(rs.getString("address"));
		emp.setRole(Role.valueOf(rs.getString("role")));
		return emp;
	}

	public Employee changePassword(String code, String oldPass, String newPass) {
		
		ValidationUtils.notEmptyString(code, "Please login again.");
		ValidationUtils.notEmptyStringInput(oldPass, "Old Password");
		ValidationUtils.notEmptyStringInput(newPass, "New Password");
		
		Employee emp = findByCode(code);
		if(!emp.getPassword().equals(oldPass)) {
			throw new BalanceException("Please check your old password.");
		}
		
		String update = "update employee set password = ? where emp_code = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(update)) {
			
			stmt.setString(1, newPass);
			stmt.setString(2, code);
			
			stmt.executeUpdate();
			
			return findByCode(code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
	}


}
