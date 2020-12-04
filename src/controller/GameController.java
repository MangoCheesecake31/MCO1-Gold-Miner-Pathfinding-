package controller;

import model.*;
import javafx.fxml.FXML;
import java.util.Scanner;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.util.Duration;


public class GameController extends Controller {
	// // // Attributes	
    @FXML
    private ScrollPane gridScrollPane = new ScrollPane();

    @FXML
    private Label scanCountLabel = new Label();

    @FXML
    private Label moveCountLabel = new Label();

    @FXML
    private Label rotateCountLabel = new Label();

    @FXML
    private Label totalCountLabel = new Label();

    @FXML
    private Slider speedSlider = new Slider();

    @FXML
    private Button startButton = new Button(); 

    @FXML
    private Button backButton = new Button(); 

    @FXML
    private Label gameOverLabel = new Label();

    @FXML
    private Label lastScannedLabel = new Label();

    @FXML
    private Label speedCountLabel = new Label();

    public final String PIT_URL = "/resources/hole.png";
    public final String GOLD_URL = "/resources/gold-ingots.png";
    public final String MINER_URL = "/resources/miner.png";
    public final String EMPTY_URL = "/resources/empty.png";
    public final String BEACON_URL = "/resources/lighthouse.png";

    private double speed = 1;
    private Grid map;
    private Miner agent;
    private boolean started = false;
    private boolean automated = false;
    private GridPane grid_pane;
    private Player auto;
    private Timeline timeline;

    // // // Methods
	public void start() {
        // Setup GridPane to ScrollPane
        grid_pane = new GridPane(); 
        grid_pane.setGridLinesVisible(true);
        gridScrollPane.setContent(grid_pane);
        gameOverLabel.setOpacity(0); 

        // Read map
        readFile();

        // GridPane settings
        for (int i = 0; i < map.getSize(); i++) {
            ColumnConstraints column = new ColumnConstraints(100);
            RowConstraints row = new RowConstraints(100);

            grid_pane.getColumnConstraints().add(column);
            grid_pane.getRowConstraints().add(row);
        }

        // Miner
        agent = new Miner(map);

        // Render?
        String type;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                
                // Create images/render special cells only (Excluding null & empty cells)
                if (!(map.getCell(i, j).getType().equals("EMPTY") || map.getCell(i, j).getType() == null)) {
                    
                    // Get image source
                    type = getTypeURL(map.getCell(i, j).getType());
                    grid_pane.add(createImageView(type), i, j, 1, 1); 
                }
            }
        }

        // Player (Auto)

        // ADD MODE mainController.GAMEMODE
        auto = new Player(map);


    }

    public void updateCell(int x, int y) {
        String type = getTypeURL(map.getCell(x, y).getType());
        updateImageView(x, y, type);
    }

    // Creates an instance of a Grid object by reading the file that contains the specifications of the level.
    private void readFile() {
        try {
            Scanner sc = new Scanner(mainController.filepath);

            int map_size = sc.nextInt();    
            int num_cell = sc.nextInt();    

            // Gold Cell
            int cell_x = sc.nextInt();
            int cell_y = sc.nextInt();
            String type;

            // Grid Instance
            map = new Grid(map_size);
            map.updateCell(cell_x, cell_y, "GOLD", "GOLD");

            // Other Cells
            for (int i = 0; i < num_cell; i++) {
                cell_x = sc.nextInt();
                cell_y = sc.nextInt();
                type = sc.next();

                map.updateCell(cell_x, cell_y, type, type);
            }

        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    // // // Events
    @FXML
    public void onStartButtonClick() {
        if (!(started)) {
            // Start button
            start();
            started = true;
            startButton.setText("Next");

        } else {
            // Next button
            System.out.println("Next Move");

            // Status
            if (agent.checkWin()) {
                gameOverLabel.setText("You Win!");
                System.out.println("You Win! You found the gold :)");
                gameOverLabel.setOpacity(1.0);

                if (automated) {
                    timeline.stop();
                }
               
                // Restart
                started = false;
                automated = false;
                startButton.setText("Start");
                return;

            } else if (agent.checkLose()) {
                gameOverLabel.setText("You Lose!");
                System.out.println("You Lose! You fell into a pit :(");
                gameOverLabel.setOpacity(1.0);

                if (automated) {
                    timeline.stop();
                }

                // Restart
                started = false;
                automated = false;
                startButton.setText("Start");
                return;
            }

            // Controls
            String scanned;
            switch (auto.getNextMove()) {   
                // Move
                case 'M' -> {
                    agent.move();

                    // Update images based on direction
                    switch (agent.getFront()) {
                        case "UP"    -> {
                            updateCell(agent.x, agent.y);
                            updateCell(agent.x, agent.y + 1);
                        }

                        case "RIGHT" -> {
                            updateCell(agent.x, agent.y);
                            updateCell(agent.x - 1, agent.y);
                        }

                        case "DOWN" -> {
                            updateCell(agent.x, agent.y);
                            updateCell(agent.x, agent.y - 1);
                        }

                        case "LEFT" -> {
                            updateCell(agent.x, agent.y);
                            updateCell(agent.x + 1, agent.y);
                        }
                    }
                } 
                    
                // Rotate
                case 'R' -> {
                    agent.rotate();
                    updateCell(agent.x, agent.y);
                }

                // Scan
                case 'S' -> {
                    scanned = agent.scan();
                    // Update Scanned ImageView
                    if (scanned != null) {
                        lastScannedLabel.setText(scanned);

                    } else {     
                        lastScannedLabel.setText("EDGE");

                    }
                } 
            }

            // Update counters (Dashboard)
            scanCountLabel.setText(Integer.toString(agent.getScanCount())); 
            moveCountLabel.setText(Integer.toString(agent.getMoveCount()));
            rotateCountLabel.setText(Integer.toString(agent.getRotateCount())); 
            totalCountLabel.setText(Integer.toString(agent.getScanCount() + agent.getMoveCount() + agent.getRotateCount())); 
        }
    }

    @FXML
    public void onBackButtonClick(Event event) {
        if (automated) {
            timeline.stop();
        }

        mainController.changeScene(mainController.MAIN_VIEW);
    }

    @FXML
    public void onAutoButtonClick() {
        // Timeline
        if (!(automated) ) {
            // Get Speed
            speed = speedSlider.getValue();
            speedCountLabel.setText(Double.toString(speed));

            timeline = new Timeline(new KeyFrame(Duration.millis(1000 - (speed - 1) * 100), event -> onStartButtonClick()));
            
            System.out.println("Automatic Mode");
            automated = true;
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    // Creates an instance of a ImageView object that is resized to fit the GUI
    private ImageView createImageView(String url) {
        ImageView image = new ImageView(url);
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setPreserveRatio(true);

        return image;
    }

    // Updates the image of an instance of a ImageView object that is resized to fit the GUI
    private void updateImageView(int x, int y, String url) {
        Image image = new Image(url);
        // Search current children
        for (Node node: grid_pane.getChildren()) {
            if (node instanceof ImageView && (grid_pane.getRowIndex(node) == y && grid_pane.getColumnIndex(node) == x)) {
                ImageView cell = (ImageView) node;
                cell.setImage(image);
                return;
            }
        }

        // Add new child
        grid_pane.add(createImageView(url), x, y, 1, 1); 
    }

    private String getTypeURL(String type) {
        switch (type) {
            case "PIT":     return PIT_URL;
            case "GOLD":    return GOLD_URL;
            case "MINER":   return MINER_URL;
            case "EMPTY":   return EMPTY_URL;
            case "BEACON":  return BEACON_URL;
            default:        return " ";
        }
    }
} 
