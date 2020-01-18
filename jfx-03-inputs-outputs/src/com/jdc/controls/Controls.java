package com.jdc.controls;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controls extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {

		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Controls.fxml"))));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	

	@FXML
	private void loadView(ActionEvent event) {
		
		try {
			
			Node node = (Node) event.getSource();
			String viewName = String.format("%s.fxml", node.getId());
				
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(viewName))));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
