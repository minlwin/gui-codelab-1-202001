package com.jdc.accounting.views;

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

    @FXML
    private void close() {

    }

    @FXML
    private void save() {

    }

	public static void show() {

		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			FXMLLoader loader = new FXMLLoader(EmployeeEdit.class.getResource("EmployeeEdit.fxml"));
			stage.setScene(new Scene(loader.load()));
			
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
