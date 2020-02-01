package com.jdc.accounting.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountHome {

	public static void show() {
		try {
			
			Parent root = FXMLLoader.load(AccountHome.class.getResource("AccountHome.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
