package driver;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DriverGUI extends Application {
	// // // Methods
	public static void main(String[] args) {
		System.out.println("Launching...");
		launch(args);	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new MainController(primaryStage);
	}
} 