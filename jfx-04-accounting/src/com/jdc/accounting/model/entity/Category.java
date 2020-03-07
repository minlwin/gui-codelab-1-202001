package com.jdc.accounting.model.entity;

public class Category {

	private int id;
	private BalanceType type;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BalanceType getType() {
		return type;
	}

	public void setType(BalanceType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
