package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
	// // // Attributes
	private Stage primaryStage;

	public static final int MAIN_VIEW = 1;
	public static final int GAME_VIEW = 2;


	// // // Constructors
	public MainController(Stage stage) {
		
		// Stage setup
		primaryStage = stage;
		primaryStage.setTitle("Gold Miner Game");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        changeScene(MAIN_VIEW);
	}


	// // // Methods
	public void changeScene(int view) {
		FXMLLoader loader = new FXMLLoader();

		String filename;
		switch (view) {
			case MAIN_VIEW -> filename = "MainMenu";
			case GAME_VIEW -> filename = "GameMenu";
			default 	   -> filename = "";
		}

		// Get FXML file
        loader.setLocation(getClass().getResource("/view/" + filename + ".fxml"));

        // Set scene
        try {
            primaryStage.setScene(new Scene(loader.load()));
           
        } catch (IOException e) {
            e.printStackTrace();

        }

        Controller controller = loader.getController();
        controller.setMainController(this);

        // Display
        primaryStage.show();
	}
}