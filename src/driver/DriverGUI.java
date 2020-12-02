package driver;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DriverGUI extends Application {
	// // // Methods
	public static void main(String[] args) {
		System.out.println("What");
		launch(args);	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/View/MainMenu.fxml"));
		System.out.println(getClass().getResource("/View/MainMenu.fxml"));

		primaryStage.setTitle("MCO1 Gold Model.Miner");
		primaryStage.setScene(new Scene(loader.load()));
		primaryStage.setResizable(false);

		// Display	
		primaryStage.show();
	}
} 