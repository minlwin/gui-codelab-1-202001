package com.jdc.accounting.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BalanceManagement {
	
	@FXML
	private Label title;

	public void init(boolean incomes) {
		title.setText(incomes ? "Incomes Management" : "Expenses Management");
	}

}
