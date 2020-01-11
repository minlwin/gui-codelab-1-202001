package com.jdc.simple.calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {
	
	
	
	@Override
	public void start(Stage stage) throws Exception {		
		AnchorPane root = FXMLLoader.load(getClass().getResource("SimpleCalculator.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
