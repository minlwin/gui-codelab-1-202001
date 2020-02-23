package com.jdc.accounting.views;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.BalanceException;
import com.jdc.accounting.model.EmployeeModel;
import com.jdc.accounting.model.entity.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePass {

    @FXML
    private Label message;

    @FXML
    private PasswordField oldPass;

    @FXML
    private PasswordField newPass;

    @FXML
    void close() {
    	message.getScene().getWindow().hide();
    }

    @FXML
    void save() {
    	
    	try {
    		
    		EmployeeModel model = new EmployeeModel();
    		
    		Employee employee = model.changePassword(ApplicationContext.getLoginUser().getCode(), 
    				oldPass.getText(), 
    				newPass.getText());
    		
    		ApplicationContext.setLoginUser(employee);
    		
    		close();
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Message");
    		alert.setContentText("Change Password has finish successfully!");
    		alert.show();
			
		} catch (BalanceException e) {
			message.setText(e.getMessage());
		}

    }

	public static void show() {
		try {
			
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(FXMLLoader.load(ChangePass.class.getResource("ChangePass.fxml"))));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
