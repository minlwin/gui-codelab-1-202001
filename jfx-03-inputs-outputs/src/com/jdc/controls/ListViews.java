package com.jdc.controls;

import java.util.List;

import com.jdc.controls.model.Course;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class ListViews implements SceneInitController{

    @FXML
    private TextField stringInput;

    @FXML
    private ListView<String> stringListView;

    @FXML
    private TextField objectInput;

    @FXML
    private ListView<Course> objectListView;
    
    @FXML
    private void initialize() {  	
    	
		ContextMenu menu = new ContextMenu();
		stringListView.setContextMenu(menu);

		stringListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
    	stringListView.setOnMouseClicked(event -> {
    		if(event.getButton() == MouseButton.SECONDARY) {
    			
    			menu.getItems().clear();
    			
    			List<String> list = stringListView.getSelectionModel().getSelectedItems();
    			
    			if(list.size() == 1) {
    				String str = stringListView.getSelectionModel().getSelectedItem();
    				menu.getItems().add(new MenuItem(str));
    			} else {
    				for(String str : list) {
        				menu.getItems().add(new MenuItem(str));
    				}
    			}
    		}
    	});
    	
    	stringInput.setOnKeyPressed(event -> {
    		if(event.getCode() == KeyCode.ENTER) {
    			
    			String value = stringInput.getText();
    			if(!value.isEmpty()) {
    				stringListView.getItems().add(value);
    				stringInput.clear();
    			}
    		}
    	});
    	
    	objectInput.setOnKeyPressed(event -> {
    		if(event.getCode() == KeyCode.ENTER) {
    			
    			if(!objectInput.getText().isEmpty()) {
        			Course c = new Course();
        			c.setName(objectInput.getText());
        			c.setId(objectListView.getItems().size() + 1);
        			objectListView.getItems().add(c);
        			objectInput.clear();
    			}
    		}
    	});
    	
    	objectListView.setEditable(true);
    	objectListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<Course>() {

			@Override
			public String toString(Course object) {
				return object.getName();
			}

			@Override
			public Course fromString(String string) {
				Course c = objectListView.getSelectionModel().getSelectedItem();
				
				if(null != c) {
					c.setName(string);
				}
				return c;
			}
		}));
    	
    	stringListView.setEditable(true);
    	stringListView.setCellFactory(TextFieldListCell.forListView());
    }
    
    

	@Override
	public void initScene() {
		
		EventHandler<MouseEvent> stringListClear = event -> {
    		stringListView.getSelectionModel().clearSelection();
    	};
		
    	stringInput.getScene().setOnMouseClicked(stringListClear);
    	stringInput.setOnMouseClicked(stringListClear);
    	objectInput.setOnMouseClicked(stringListClear);
    	objectListView.setOnMouseClicked(stringListClear);
	}

}
