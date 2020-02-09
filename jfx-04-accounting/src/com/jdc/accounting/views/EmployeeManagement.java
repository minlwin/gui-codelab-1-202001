package com.jdc.accounting.views;

import com.jdc.accounting.model.entity.Employee;

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
    	EmployeeEdit.show(this::save);
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
    	
    	System.out.println("Reload Table");
    }

	private void save(Employee emp) {

		search();
	}

}
