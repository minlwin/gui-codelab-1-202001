package com.jdc.accounting.views;

import java.util.List;

import com.jdc.accounting.model.EmployeeModel;
import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EmployeeManagement {

    @FXML
    private ComboBox<Role> schRole;

    @FXML
    private TextField schCode;

    @FXML
    private TextField schName;

    @FXML
    private TableView<Employee> table;
    
    private EmployeeModel model;
    
    @FXML
    private void initialize()  {
    	model = new EmployeeModel();
    	schRole.getItems().addAll(Role.values());
    	search();
    	
    	MenuItem edit = new MenuItem("Edit Employee");
    	edit.setOnAction(event -> {
    		Employee emp = table.getSelectionModel().getSelectedItem();
    		EmployeeEdit.show(emp, this::save);
    	});
    	
    	table.setContextMenu(new ContextMenu(edit));
    }

    @FXML
    private void addNew() {
    	EmployeeEdit.show(null, this::save);
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
    	List<Employee> list = model.search(schRole.getValue(), schCode.getText(), schName.getText());
    	
    	// clear table items
    	table.getItems().clear();
    	
    	// add search result to table
    	table.getItems().addAll(list);
    }

	private void save(Employee emp) {
		model.save(emp);
		search();
	}

}
