package com.jdc.simple.calc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {
	
	@FXML
	private TextField input1;
	@FXML
	private TextField input2;
	@FXML
	private Label result;
	
	@FXML
	private void calculate(ActionEvent event) {
		try {
			Button btn = (Button) event.getSource();
			
			double d1 = Double.valueOf(input1.getText());
			double d2 = Double.valueOf(input2.getText());
			double d3 = 0.0;
			
			switch (btn.getText()) {
			case "+":
				d3 = d1 + d2;
				break;
			case "-":
				d3 = d1 - d2;
				break;
			case "*":
				d3 = d1 * d2;
				break;
			case "/":
				d3 = d1 / d2;
				break;

			default:
				break;
			}
			
			result.setText(String.valueOf(d3));			
		} catch (NumberFormatException e) {
			result.setText("digit only!");
		}
	}
		
	@Override
	public void start(Stage stage) throws Exception {		
		Pane root = FXMLLoader.load(getClass().getResource("SimpleCalculator.fxml"));
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
