package application;

import BasicGUI.ControlHelp;
import BasicGUI.GameLogin;
import BasicGUI.Scoreboard;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.*;

import java.io.IOException;

public class Homepage {
    private Homepage() {
    }

    private static Homepage instance = new Homepage();

    public static Homepage getInstance() {
        return instance;
    }

    //main game page Object
    private AsteroidsApplication asteroidsApp = new AsteroidsApplication();

    public void load(Stage stage) {

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(GameLogin.WIDTH, GameLogin.HEIGHT);
        borderPane.setStyle("-fx-background-color: black;");


        // Create the title label
        Label titleLabel = new Label("Asteroids");
        titleLabel.setStyle("-fx-font-size: 80px; -fx-font-family: 'Impact'; -fx-text-fill: white;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);


        // Create the start button
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;");
        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-font-size: 36px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;"));
        startButton.setOnAction(e -> {
            try {
                asteroidsApp.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        BorderPane.setAlignment(startButton, Pos.CENTER);

        // Create the controls button
        Button controlsButton = new Button("Controls");
        controlsButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;");
        controlsButton.setOnMouseEntered(e -> controlsButton.setStyle("-fx-font-size: 36px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        controlsButton.setOnMouseExited(e -> controlsButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;"));
        controlsButton.setOnAction(e -> {
            ControlHelp.show(stage);
        });
        BorderPane.setAlignment(controlsButton, Pos.CENTER);

        // Create the leaderboard button
        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;");
        leaderboardButton.setOnMouseEntered(e -> leaderboardButton.setStyle("-fx-font-size: 36px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        leaderboardButton.setOnMouseExited(e -> leaderboardButton.setStyle("-fx-font-size: 36px; -fx-background-color: transparent; -fx-text-fill: white;"));
        leaderboardButton.setOnAction(e -> {
            try {
                Scoreboard.show(stage,GameLogin.getInstance().playerlist);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        BorderPane.setAlignment(leaderboardButton, Pos.CENTER);

        // Create the button container
        VBox buttonContainer = new VBox(20, startButton, controlsButton, leaderboardButton);
        buttonContainer.setAlignment(Pos.CENTER);
        BorderPane.setMargin(buttonContainer, new javafx.geometry.Insets(50));

        // Set the nodes in the BorderPane
        borderPane.setTop(titleLabel);
        borderPane.setCenter(buttonContainer);

        // Create the scene
        stage.getScene().setRoot(borderPane);
    }
}
