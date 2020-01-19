package com.jdc.controls;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ListViews implements SceneInitController{

    @FXML
    private TextField stringInput;

    @FXML
    private ListView<String> stringListView;

    @FXML
    private TextField objectnput;

    @FXML
    private ListView<?> objectListViiew;
    
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
    }

	@Override
	public void initScene() {
		
		EventHandler<MouseEvent> stringListClear = event -> {
    		stringListView.getSelectionModel().clearSelection();
    	};
		
    	stringInput.getScene().setOnMouseClicked(stringListClear);
    	stringInput.setOnMouseClicked(stringListClear);
    	objectnput.setOnMouseClicked(stringListClear);
    	objectListViiew.setOnMouseClicked(stringListClear);
	}

}
