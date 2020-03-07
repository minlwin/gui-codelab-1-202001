package com.jdc.accounting.views;

import com.jdc.accounting.model.entity.Balance;
import com.jdc.accounting.model.entity.BalanceType;

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
    private TableView<Balance> table;
    
    private BalanceType type;

    @FXML
    private void addNew() {
    	try {
    		Balance balance = new Balance();
    		balance.setType(type);
        	Parent view = BalanceDetails.getView(balance);
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

	public void init(BalanceType type) {
		this.type = type;
		title.setText(type == BalanceType.Incomes ? "Incomes Management" : "Expenses Management");
	}

}
