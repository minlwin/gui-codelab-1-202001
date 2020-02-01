package com.jdc.controls;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.jdc.controls.model.Student;
import com.jdc.controls.model.StudentModel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    
    private StudentModel model;
    
    @FXML
    private void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	model = StudentModel.getInstance();
    	
    	reloadTable();
    	
    	Function<Supplier<Consumer<String>>, EventHandler<TableColumn.CellEditEvent<Student,String>>> function 
    		= supplier -> event -> {
    			supplier.get().accept(event.getNewValue());
    		};
    		
    		
    	table.setEditable(true);

    	nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	nameColumn.setOnEditCommit(function.apply(() -> table.getSelectionModel().getSelectedItem()::setName));

    	phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	phoneColumn.setOnEditCommit(function.apply(() -> table.getSelectionModel().getSelectedItem()::setPhone));

    	emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	emailColumn.setOnEditCommit(function.apply(() -> table.getSelectionModel().getSelectedItem()::setEmail));

    	addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	addressColumn.setOnEditCommit(function.apply(() -> table.getSelectionModel().getSelectedItem()::setAddress));
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
