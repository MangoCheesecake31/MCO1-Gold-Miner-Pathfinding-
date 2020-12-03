package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MenuController extends Controller {
    private FileChooser fc;


    // // // Methods
    public void initialize() {
        fc = new FileChooser();
        fc.setTitle("Locate Map File");
    }


	// // // Events
    @FXML
    public void onRandomButtonClick(Event event) {
        System.out.println("Random Mode");

        mainController.setFilePath(fc.showOpenDialog(new Stage()));
        mainController.setGameMode("RANDOM"); 

        mainController.changeScene(mainController.GAME_VIEW);
    }

    @FXML
    public void onSmartButtonClick(Event event) {
        System.out.println("Smart Mode");

        mainController.setFilePath(fc.showOpenDialog(new Stage()));
        mainController.setGameMode("SMART"); 

        mainController.changeScene(mainController.GAME_VIEW);
    }
} 