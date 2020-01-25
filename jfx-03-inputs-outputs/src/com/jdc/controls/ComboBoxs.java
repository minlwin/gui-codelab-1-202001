package com.jdc.controls;

import com.jdc.controls.model.Course;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class ComboBoxs {

    @FXML
    private ComboBox<String> string;

    @FXML
    private ComboBox<Course> object;

    @FXML
    private ComboBox<String> editable;

    @FXML
    private ComboBox<String> addable;
    
    @FXML
    private void initialize() {
    	
    	string.getItems().addAll("Java", "Kotlin", "Javascript", "Python");
    	
    	object.getItems().addAll(
    			new Course(1, "Java SE"),
    			new Course(1, "Java EE"),
    			new Course(1, "Spring"),
    			new Course(1, "One Stop")
    			
    	);

    	editable.getItems().addAll("Java", "Kotlin", "Javascript", "Python");
    	
    	IntegerProperty lastIndex = new SimpleIntegerProperty(-1);
    	lastIndex.bind(editable.getSelectionModel().selectedIndexProperty());
    	
    	editable.setOnMouseClicked(event -> {
    		if(event.getClickCount() == 2) {
    			editable.setEditable(true);
    		}
    	});
    	
    	editable.setOnKeyPressed(event -> {
    		if(event.getCode() == KeyCode.ENTER) {
    			
    			String value = editable.getValue();
    			
    			if(!editable.getItems().contains(value)) {
    				if(lastIndex.intValue() == -1) {
            			editable.getItems().add(value);
    				} else {
    					editable.getItems().set(lastIndex.intValue(), value);
    				}
    			}
    			editable.setEditable(false);
    		}
    	});
    	
    }

}
