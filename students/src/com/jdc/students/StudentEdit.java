package com.jdc.students;

import java.util.function.Consumer;

import com.jdc.students.domain.Student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentEdit {

    @FXML
    private Label message;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private TextArea address;
    
    private Consumer<Student> saveListener;
    
    public static void show(Consumer<Student> saveListener) {
    	try {
			
    		FXMLLoader loader = new FXMLLoader(StudentEdit.class.getResource("StudentEdit.fxml"));
    		Parent root = loader.load();
    		
    		StudentEdit controller = loader.getController();
    		controller.saveListener = saveListener;
    		
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.show();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void close() {
    	name.getScene().getWindow().hide();
    }

    @FXML
    void save() {
    	try {
        	Student student = getViewData();
        	saveListener.accept(student);
        	close();
		} catch (Exception e) {
			message.setText(e.getMessage());
		}
    }

	private Student getViewData() {
		Student student = new Student();
		student.setName(name.getText());
		student.setPhone(phone.getText());
		student.setEmail(email.getText());
		student.setAddress(address.getText());
		return student;
	}

}
