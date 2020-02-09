package com.jdc.accounting.views;

import java.util.function.Consumer;

import com.jdc.accounting.model.BalanceException;
import com.jdc.accounting.model.entity.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmployeeEdit {

    @FXML
    private Label message;

    @FXML
    private TextField name;

    @FXML
    private TextField code;

    @FXML
    private ComboBox<?> role;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private TextArea address;
    
    private Consumer<Employee> saveListener;
    
    @FXML
    private void close() {
    	message.getScene().getWindow().hide();
    }

    @FXML
    private void save() {
    	
    	try {
			
    		Employee emp = getViewData();
    		
    		saveListener.accept(emp);
    		
    		close();
    		
		} catch (BalanceException e) {
			e.printStackTrace();
		}

    }

	private Employee getViewData() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void show(Consumer<Employee> listener) {

		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader loader = new FXMLLoader(EmployeeEdit.class.getResource("EmployeeEdit.fxml"));
			stage.setScene(new Scene(loader.load()));
			
			EmployeeEdit controller = loader.getController();
			controller.saveListener = listener;
			
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
