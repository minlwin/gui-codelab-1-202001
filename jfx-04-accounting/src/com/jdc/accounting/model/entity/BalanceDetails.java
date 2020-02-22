package com.jdc.accounting.model.entity;

public class BalanceDetails {

	private int id;
	private String title;
	private String remark;
	private int amount;
	private int balanceId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
	}

}
