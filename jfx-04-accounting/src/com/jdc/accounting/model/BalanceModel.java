package com.jdc.accounting.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.accounting.model.entity.Balance;
import com.jdc.accounting.model.entity.BalanceDetail;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.utils.ConnectionManager;
import com.jdc.accounting.utils.StringUtils;
import com.jdc.accounting.utils.ValidationUtils;

import javafx.collections.ObservableList;

public class BalanceModel {

	public void create(Balance balance, ObservableList<BalanceDetail> items) {
		
		validate(balance);
		validate(items);
		
		String balanceInsert = "insert into balance (category_id, business_date, total, remark, type, employee_emp_code) values (?, ?, ?, ?, ?, ?)";
		String detailsInsert = "insert into balance_details (balance_id, title, amount, remark) values (?, ?, ?, ?)";

		try (Connection conn = ConnectionManager.getConnection(); 
				PreparedStatement balanceStatement = conn.prepareStatement(balanceInsert, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement detailsStatement = conn.prepareStatement(detailsInsert)) {
			
			balanceStatement.setInt(1, balance.getCategory().getId());
			balanceStatement.setDate(2, Date.valueOf(balance.getDate()));
			balanceStatement.setInt(3, balance.getTotal());
			balanceStatement.setString(4, balance.getRemark());
			balanceStatement.setInt(5, balance.getType().ordinal());
			balanceStatement.setString(6, balance.getEmployee().getCode());
			
			balanceStatement.executeUpdate();
			
			ResultSet rs = balanceStatement.getGeneratedKeys();
			
			while(rs.next()) {
				
				int id = rs.getInt(1);
				
				for(BalanceDetail d : items) {
					detailsStatement.setInt(1, id);
					detailsStatement.setString(2, d.getTitle());
					detailsStatement.setInt(3, d.getAmount());
					detailsStatement.setString(4, d.getRemark());
					
					detailsStatement.addBatch();
				}
				
				detailsStatement.executeBatch();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void validate(ObservableList<BalanceDetail> items) {

		ValidationUtils.notEmptyList(items, "Balance Detail");
		
		for(BalanceDetail d : items) {
			ValidationUtils.notNull(d, "Balance Details");
		}
	}

	private void validate(Balance balance) {
		
		ValidationUtils.notNull(balance.getCategory(), "Category");
		ValidationUtils.notNullSelect(balance.getDate(), "Business Date");
		
	}

	public List<Balance> search(BalanceType type, Category category, LocalDate from, LocalDate to, String employee) {
		List<Balance> result = new ArrayList<>();
		
		String sql = "select b.id, b.category_id, c.name, b.business_date, b.total, b.remark, b.type, b.employee_emp_code, e.name "
				+ "from balance b "
				+ "join category c on b.category_id = c.id "
				+ "join employee e on e.emp_code = b.employee_emp_code "
				+ "where 1 = 1";
		StringBuilder sb = new StringBuilder(sql);
		List<Object> params = new ArrayList<>();
		
		if(null != category) {
			sb.append(" and b.category_id = ?");
			params.add(category.getId());
		}
		
		if(null != type) {
			sb.append(" and b.type = ?");
			params.add(type.ordinal());
		}
		
		if(null != from) {
			sb.append(" and b.business_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to) {
			sb.append(" and b.business_date <= ?");
			params.add(Date.valueOf(to));
		}

		if(!StringUtils.isEmpty(employee)) {
			sb.append(" and (LOWER(b.employee_emp_code) like ? or LOWER(e.name) like ?)");
			params.add(employee.toLowerCase().concat("%"));
			params.add(employee.toLowerCase().concat("%"));
		}
		
		try (Connection conn = ConnectionManager.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getData(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private Balance getData(ResultSet rs) throws SQLException {
		
		Balance b = new Balance();
		Category c = new Category();
		b.setCategory(c);
		
		b.setId(rs.getInt(1));
		c.setId(rs.getInt(2));
		c.setName(rs.getString(3));
		
		b.setDate(rs.getDate(4).toLocalDate());
		b.setTotal(rs.getInt(5));
		b.setRemark(rs.getString(6));
		b.setType(BalanceType.values()[rs.getInt(7)]);
		c.setType(b.getType());
		
		Employee e = new Employee();
		b.setEmployee(e);
		
		e.setCode(rs.getString(8));
		e.setName(rs.getString(9));
		
		return b;
	}

}
