package com.jdc.accounting.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.utils.ConnectionManager;

public class CategoryModel {

	public List<Category> search(BalanceType type, String name) {
		
		List<Category> result = new ArrayList<>();
		String sql = "select * from category where type = ? and LOWER(name) like ?";
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, type.ordinal());
			stmt.setString(2, name.toLowerCase().concat("%"));
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getData(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Category create(BalanceType type, String name) {

		String sql = "insert into category (type, name) values (?, ?)";
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, type.ordinal());
			stmt.setString(2, name);
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			while(rs.next()) {
				return findById(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Category findById(int id) {
		String sql = "select * from category where id = ?";
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getData(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Category getData(ResultSet rs) throws SQLException {
		
		Category c = new Category();
		c.setId(rs.getInt(1));
		c.setType(BalanceType.values()[rs.getInt(2)]);
		c.setName(rs.getString(3));
		
		return c;
	}
}
