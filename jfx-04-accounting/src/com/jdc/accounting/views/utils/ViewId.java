package com.jdc.accounting.views.utils;

public enum ViewId {
	Home("EmployeeHome", false), 
	Incomes("BalanceManagement", false), 
	Expenses("BalanceManagement", false), 
	Balances("BalanceReport", true), 
	Employees("EmployeeManagement", true);
	
	private String view;
	private boolean onlyAdmin;

	private ViewId(String view, boolean adminMenu) {
		this.view = view;
		this.onlyAdmin = adminMenu;
	}
	
	public String getView() {
		return view.concat(".fxml");
	}
	
	public boolean isOnlyAdmin() {
		return onlyAdmin;
	}
}
