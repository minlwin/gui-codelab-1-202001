package com.jdc.accounting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String URL = "jdbc:mariadb://localhost:3306/account_jdbc_db";
	private static final String USER = "root";
	private static final String PASS = "admin";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
