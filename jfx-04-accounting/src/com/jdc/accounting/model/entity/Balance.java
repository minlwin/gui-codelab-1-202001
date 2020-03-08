package com.jdc.accounting.model.entity;

import java.time.LocalDate;

public class Balance {

	private int id;
	private LocalDate date;
	private int total;
	private Employee employee;
	private BalanceType type;
	private Category category;
	private String remark;
	
	public String getEmployeeName() {
		return employee.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public BalanceType getType() {
		return type;
	}

	public void setType(BalanceType type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
