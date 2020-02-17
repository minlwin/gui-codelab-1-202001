package com.jdc.students;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentApplication extends Application{
	
	private static EntityManagerFactory EMF;
	
	@Override
	public void init() throws Exception {
		EMF = Persistence.createEntityManagerFactory("students");
	}
	
	@Override
	public void stop() throws Exception {
		EMF.close();
	}
	
	public static EntityManager getEntityManager() {
		return EMF.createEntityManager();
	}
	

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("StudentList.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
