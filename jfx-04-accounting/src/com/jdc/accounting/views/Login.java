package com.jdc.accounting.views;

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

    @FXML
    private void close() {
    	password.getScene().getWindow().hide();
    }

    @FXML
    private void login() {
    	
    	try {
    		
    		// Do Login 
    		
    		// set login user to application context
    		
    		// show account home
    		AccountHome.show();
    		
    		// close login window
    		close();
			
		} catch (Exception e) {
			message.setText(e.getMessage());
		}

    }

}
