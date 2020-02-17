package com.jdc.students;

import java.util.List;
import java.util.function.BiConsumer;

import com.jdc.students.domain.Student;
import com.jdc.students.domain.StudentService;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class StudentList {

    @FXML
    private TextField schName;

    @FXML
    private TextField schPhone;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colPhone;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, String> colAddress;
    
    private StudentService service;
    
    @FXML
    private void initialize() {
    	
    	service  = new StudentService(StudentApplication.getEntityManager());   	
    	table.setEditable(true);
    	
    	setEditable(colName, (student, value) -> student.setName(value));
    	setEditable(colPhone, (student, value) -> student.setPhone(value));
    	setEditable(colEmail, (student, value) -> student.setEmail(value));
    	setEditable(colAddress, (student, value) -> student.setAddress(value));
    }
    
    private void setEditable(TableColumn<Student, String> column, BiConsumer<Student, String> setter) {
    	column.setCellFactory(TextFieldTableCell.forTableColumn());
    	column.setOnEditCommit(event -> {
    		
    		if(!event.getOldValue().equals(event.getNewValue())) {
        		setter.accept(event.getRowValue(), event.getNewValue());
        		service.save(event.getRowValue());
    		}
    		
    	});
    }

    @FXML
    void addNew() {
    	StudentEdit.show(this::save);
    }

    @FXML
    void search() {
    	List<Student> list = service.search(schName.getText(), schPhone.getText());
    	table.getItems().clear();
    	table.getItems().addAll(list);
    }
    
    private void save(Student student) {
    	service.save(student);
    	search();
    }

}
