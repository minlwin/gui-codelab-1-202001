package com.jdc.accounting.views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BalanceManagement {

    @FXML
    private Label title;

    @FXML
    private ComboBox<?> schCategory;

    @FXML
    private TextField schEmployee;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TableView<?> table;
    
    private boolean income;

    @FXML
    private void addNew() {
    	try {
        	Parent view = BalanceDetails.getView(income);
        	AccountHome.getContentManager().setContentView(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    private void clear() {
    	
    	schCategory.setValue(null);
    	schEmployee.clear();
    	dateFrom.setValue(null);
    	dateTo.setValue(null);
    	table.getItems().clear();
    }

    @FXML
    private void search() {

    }

	public void init(boolean income) {
		this.income = income;
		title.setText(income ? "Incomes Management" : "Expenses Management");
	}

}
