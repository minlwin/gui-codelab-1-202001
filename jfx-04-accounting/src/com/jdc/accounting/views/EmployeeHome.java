package com.jdc.accounting.views;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.EmployeeModel;
import com.jdc.accounting.model.entity.Employee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeHome {

    @FXML
    private Label code;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Label phone;

    @FXML
    private Label email;
    
    @FXML
    private void initialize() {
    	loadProfile();
    }

    @FXML
    void changePass() {
    	ChangePass.show();
    }

    @FXML
    void editProfile() {
    	EmployeeEdit.show(ApplicationContext.getLoginUser(), this::save);
    }
    
    private void save(Employee emp) {
    	
    	// save employee
    	new EmployeeModel().save(emp);
    	
    	ApplicationContext.setLoginUser(emp);
    	
    	loadProfile();
    }
    
    private void loadProfile() {
    	Employee login = ApplicationContext.getLoginUser();
    	
    	code.setText(login.getCode());
    	name.setText(login.getName());
    	role.setText(login.getRole().name());
    	phone.setText(login.getPhone());
    	email.setText(login.getEmail());
    }

}
