package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MenuController extends Controller {
	// // // Events
    @FXML
    void onRandomButtonClick(Event event) {
        System.out.println("Random");
        mainController.changeScene(2);
    }

    @FXML
    void onSmartButtonClick(Event event) {
        System.out.println("Smart");
        mainController.changeScene(2);
    }
} 