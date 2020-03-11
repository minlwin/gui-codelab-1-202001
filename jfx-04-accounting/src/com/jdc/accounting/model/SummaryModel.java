package com.jdc.accounting.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.utils.ConnectionManager;
import com.jdc.accounting.utils.StringUtils;

public class SummaryModel {
	
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");

	private CategoryModel categoryModel;
	
	public SummaryModel() {
		categoryModel = new CategoryModel();
	}
	
	public double find(BalanceType type, String code) {
		
		StringBuilder sb = new StringBuilder("select sum(total) from balance where type = ?");
		
		if(!StringUtils.isEmpty(code)) {
			sb.append(" and employee_emp_code = ?");
		}
		
		try (Connection conn = ConnectionManager.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			stmt.setInt(1, type.ordinal());
			
			if(!StringUtils.isEmpty(code)) {
				stmt.setString(2, code);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getDouble(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public Map<String, Map<String, Integer>> findBarData(BalanceType type, String code) {
		
		Map<String, Map<String, Integer>> result = new HashMap<>();
		
		List<Category> categories = categoryModel.search(type, null);
		
		for(Category c : categories) {
			Map<String, Integer> data = findBarData(type, c, code);
			
			if(check(data)) {
				result.put(c.getName(), data);
			}
		}
		
		return result;
	}

	private boolean check(Map<String, Integer> data) {
		
		for(Integer value : data.values()) {
			if(value > 0) {
				return  true;
			}
		}
		
		return false;
	}

	private Map<String, Integer> findBarData(BalanceType type, Category category, String code) {
		
		Map<String, Integer> result = new LinkedHashMap<>();
		LocalDate from = getStartDate(type, code);
		
		if(null != from) {
			
			StringBuilder sb = new StringBuilder("select sum(total) from balance where type = ?" 
					+ " and category_id = ?"
					+ " and business_date >= ? and business_date < ?");
			
			if(!StringUtils.isEmpty(code)) {
				sb.append(" and employee_emp_code = ?");
			}

			try (Connection conn = ConnectionManager.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sb.toString())) {

				LocalDate target = LocalDate.from(from);
				
				while(target.compareTo(LocalDate.now()) <= 0) {
					
					LocalDate to = target.plusDays(1);
					
					stmt.setInt(1, type.ordinal());
					stmt.setInt(2, category.getId());
					stmt.setDate(3, Date.valueOf(target));
					stmt.setDate(4, Date.valueOf(to));

					if(!StringUtils.isEmpty(code)) {
						stmt.setString(5, code);
					}
					
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						result.put(target.format(df), rs.getInt(1));
					}
					
					target = to;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	private LocalDate getStartDate(BalanceType type, String code) {
		
		StringBuilder sb = new StringBuilder("select min(business_date) from balance where type = ?");
		
		if(!StringUtils.isEmpty(code)) {
			sb.append(" and employee_emp_code = ?");
		}
		
		try (Connection conn = ConnectionManager.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			stmt.setInt(1, type.ordinal());
			
			if(!StringUtils.isEmpty(code)) {
				stmt.setString(2, code);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Date date = rs.getDate(1);
				return null == date ? null : date.toLocalDate();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
}
