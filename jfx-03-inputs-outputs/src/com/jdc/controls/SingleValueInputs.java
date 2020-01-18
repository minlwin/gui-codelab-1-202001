package com.jdc.controls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class SingleValueInputs {

    @FXML
    private TextField textField;

    @FXML
    private PasswordField password;

    @FXML
    private DatePicker date;

    @FXML
    private RadioButton male;

    @FXML
    private ToggleGroup gender;

    @FXML
    private HBox checkHolder;

    @FXML
    private TextArea textArea;

    @FXML
    private Label textFieldLabel;

    @FXML
    private Label passwordLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label checkLabel;

    @FXML
    private Label textAreaLabel;
    
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
    	date.setConverter(new StringConverter<LocalDate>() {
			
			@Override
			public String toString(LocalDate object) {
				if(null != object) {
					return object.format(DF);
				}
				return null;
			}
			
			@Override
			public LocalDate fromString(String string) {
				
				if(null != string && !string.isEmpty()) {
					return LocalDate.parse(string, DF);
				}
				
				return null;
			}
		});
    }
    
    @FXML
    void check(ActionEvent event) {
    	textFieldLabel.setText(textField.getText());
    	passwordLabel.setText(password.getText());
    	dateLabel.setText(null != date.getValue() ? date.getValue().format(DF) : "");
    	genderLabel.setText(male.isSelected() ? "Male" : "Female");
    	
       	String checkStr = checkHolder.getChildren().stream()
    		.filter(node -> node instanceof CheckBox)
    		.map(node -> (CheckBox)node)
    		.filter(check -> check.isSelected())
    		.map(check -> check.getText())
    		.reduce("", (subTotal, element) -> subTotal.isEmpty() ? element : subTotal.concat(", ").concat(element));
    	
    	checkLabel.setText(checkStr);
    	
    	textAreaLabel.setText(textArea.getText());
    }

    @FXML
    void clear(ActionEvent event) {
    	textField.clear();
    	password.clear();
    	date.setValue(null);
    	male.setSelected(true);
    	checkHolder.getChildren()
    		.stream()
    		.filter(node -> node instanceof CheckBox)
    		.map(node -> (CheckBox)node)
    		.forEach(check -> check.setSelected(false));
    	textArea.clear();
    	
    	textFieldLabel.setText("");
    	passwordLabel.setText("");
    	genderLabel.setText("");
    	dateLabel.setText("");
    	checkLabel.setText("");
    	textAreaLabel.setText("");
    		
    }

}
