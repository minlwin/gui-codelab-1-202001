package com.jdc.accounting.views;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BalanceDetails {
	
	@FXML
	private Label title;

    @FXML
    private TextField empCode;

    @FXML
    private TextField empName;

    @FXML
    private TextField empRole;

    @FXML
    private DatePicker businessDate;

    @FXML
    private ComboBox<?> category;

    @FXML
    private TextField total;

    @FXML
    private TextArea remark;

    @FXML
    private TextField inputTitle;

    @FXML
    private TextField inputAmount;

    @FXML
    private TextField inputRemark;

    @FXML
    private TableView<?> table;
    
    private boolean income;
    

    @FXML
    private void add() {

    }

    @FXML
    private void save() {

    }
    
    private void setType(boolean type) {
    	this.income = type;
    	title.setText(income ? "Income Details" : "Expense Details");
    	
    }

	public static Parent getView(boolean income) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(BalanceDetails.class.getResource("BalanceDetails.fxml"));
		Parent view = loader.load();
		BalanceDetails controller = loader.getController();
		controller.setType(income);
		
		return view;
	}

}
