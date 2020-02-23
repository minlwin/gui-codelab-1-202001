package com.jdc.accounting.views;

import java.util.function.Consumer;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.BalanceException;
import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;

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
	private Label title;

    @FXML
    private Label message;

    @FXML
    private TextField name;

    @FXML
    private TextField code;

    @FXML
    private ComboBox<Role> role;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private TextArea address;
    
    private Employee emp;
    private Consumer<Employee> saveListener;
       
    @FXML
    private void initialize() {
    	role.getItems().addAll(Role.values());
    	role.setDisable(!ApplicationContext.isAdamin());
    }
    
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
			message.setText(e.getMessage());
		}

    }

	private Employee getViewData() {
		emp.setAddress(address.getText());
		emp.setCode(code.getText());
		emp.setEmail(email.getText());
		emp.setName(name.getText());

		if(code.isEditable()) {
			emp.setPassword(code.getText());
		}
		
		emp.setPhone(phone.getText());
		emp.setRole(role.getValue());
		return emp;
	}

	public static void show(Employee emp, Consumer<Employee> listener) {

		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader loader = new FXMLLoader(EmployeeEdit.class.getResource("EmployeeEdit.fxml"));
			stage.setScene(new Scene(loader.load()));
			
			EmployeeEdit controller = loader.getController();
			controller.init(emp, listener);
			
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(Employee emp, Consumer<Employee> listener) {
		
		this.emp = emp;
		this.saveListener = listener;
		
		if(null == emp) {
			title.setText("Add New Employee");
			this.emp = new Employee();
		} else {
			title.setText("Edit Employee");
			name.setText(emp.getName());
			code.setText(emp.getCode());
			code.setEditable(false);
			role.setValue(emp.getRole());
			phone.setText(emp.getPhone());
			email.setText(emp.getEmail());
			address.setText(emp.getAddress());
		}
		
	}


}
