package com.jdc.accounting.views;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmployeeManagement {

    @FXML
    private ComboBox<?> schRole;

    @FXML
    private TextField schCode;

    @FXML
    private TextField schName;

    @FXML
    private TableView<?> table;

    @FXML
    private void addNew() {
    	EmployeeEdit.show();
    }

    @FXML
    private void clear() {
    	schRole.setValue(null);
    	schCode.clear();
    	schName.clear();
    	table.getItems().clear();
    }

    @FXML
    private void search() {
    	// get search fields
    	
    	// search from database with search fields
    	
    	// clear table items
    	
    	// add search result to table
    }

}
