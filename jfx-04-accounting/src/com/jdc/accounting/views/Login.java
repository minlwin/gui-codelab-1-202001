package com.jdc.accounting.views;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.AuthManager;
import com.jdc.accounting.model.BalanceException;
import com.jdc.accounting.model.EmployeeModel;
import com.jdc.accounting.model.entity.Employee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private Label message;

    @FXML
    private TextField loginId;

    @FXML
    private PasswordField password;
    
    private AuthManager auth;
    
    @FXML
    private void initialize() {
    	auth = new AuthManager(new EmployeeModel());
    }

    @FXML
    private void close() {
    	password.getScene().getWindow().hide();
    }

    @FXML
    private void login() {
    	
    	try {
    		
    		// Do Login 
    		Employee loginUser = auth.login(loginId.getText(), password.getText());
    		
    		// set login user to application context
    		ApplicationContext.setLoginUser(loginUser);
    		
    		// show account home
    		AccountHome.show();
    		
    		// close login window
    		close();
			
		} catch (BalanceException e) {
			message.setText(e.getMessage());
		}

    }

}
