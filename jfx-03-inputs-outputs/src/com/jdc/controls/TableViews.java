package com.jdc.controls;

import com.jdc.controls.model.Student;
import com.jdc.controls.model.StudentModel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViews {

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private TextField address;

    @FXML
    private TableView<Student> table;
    
    @FXML
    private TableColumn<Student, String> nameColumn;
    
    private StudentModel model;
    
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	model = StudentModel.getInstance();
    	
    	reloadTable();
    }

    private void reloadTable() {
    	table.getItems().clear();
    	table.getItems().addAll(model.getAllStudents());
	}

	@FXML
    private void addStudent() {
    	
    	try {
        	// get view data
        	// create object
        	Student student = getViewData();
        	
        	// save students
        	model.save(student);
        	
        	// reload table
        	reloadTable();
        	
        	// clear inputs
        	name.clear();
        	phone.clear();
        	email.clear();
        	address.clear();
        	
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.show();
		}
    }

	private Student getViewData() {
		Student student = new Student();
		student.setName(getString(name, "Name"));
		student.setPhone(getString(phone, "Phone"));
		student.setEmail(getString(email, "Email"));
		student.setAddress(getString(address, "Address"));
		return student;
	}

	private String getString(TextField input, String name) {
		
		String value = input.getText();
		if(value.isEmpty()) {
			throw new RuntimeException(String.format("Please enter %s!", name));
		}
		
		return value;
	}

}
