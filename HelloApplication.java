package com.example.asteroids_panels;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.layout.BorderPane;


public class HelloApplication extends Application {
    private BorderPane mainPane;
    private Pane screen1, screen2, screen3;
    private Button switchButton, leaderButton;

    @Override
    public void start(Stage primaryStage) {
        mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: black;");

        screen1 = new Pane();
        screen2 = new Pane();
        screen3 = new Pane();
        Label screen1Label = new Label("Screen 1");
        Label screen2Label = new Label("Screen 2");
        Label screen3Label = new Label("Screen 3");
        screen1Label.setTextFill(Color.WHITE);
        screen2Label.setTextFill(Color.WHITE);
        screen3Label.setTextFill(Color.WHITE);
        screen1.getChildren().add(screen1Label);
        screen2.getChildren().add(screen2Label);
        screen3.getChildren().add(screen3Label);


//        Label instructionsLabel = new Label(
//                "\n\n\nInstructions:\n\n" +
//                        "Use the arrow keys \\u2190\\u2191\\u2192\\u2193) to move the ship.\n" +
//                        "Press the space bar to fire.\n\n" +
//                        "Good luck!");

        Label instructionsLabel = new Label("\n\n\nInstructions:\n\nUse the arrow keys \u2190\u2191\u2192\u2193 to move the ship.\n\nPress the space bar to fire.\n\nGood luck!");
        instructionsLabel.setFont(Font.font("Segoe UI Symbol", FontWeight.BOLD, 16));

        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        instructionsLabel.setAlignment(Pos.CENTER);
        screen2.getChildren().add(instructionsLabel);

        Label scoreboard = new Label("\n\n\nLeaderboard screen");
        scoreboard.setFont(Font.font("Segoe UI Symbol", FontWeight.BOLD, 16));

        scoreboard.setTextFill(Color.WHITE);
        scoreboard.setAlignment(Pos.CENTER);
        screen3.getChildren().add(scoreboard);



        BorderPane.setAlignment(screen1, Pos.TOP_CENTER);
        BorderPane.setAlignment(screen2, Pos.TOP_CENTER);
        BorderPane.setAlignment(screen3, Pos.TOP_CENTER);

        switchButton = new Button("View Controls");
        switchButton.setOnAction(event -> switchScreen());
        switchButton.setTextFill(Color.WHITE);
        switchButton.setStyle("-fx-background-color: black;");
        mainPane.setCenter(switchButton);

        leaderButton = new Button("View Leaderboard");
        leaderButton.setOnAction(event -> switchLeaderboard());
        leaderButton.setTextFill(Color.WHITE);
        leaderButton.setStyle("-fx-background-color: black;");
        mainPane.setBottom(leaderButton);

        mainPane.setAlignment(switchButton, Pos.CENTER);
        mainPane.setAlignment(leaderButton, Pos.BOTTOM_CENTER);
        mainPane.setTop(screen1);
        Scene scene = new Scene(mainPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void switchScreen() {
        if (mainPane.getTop() == screen1) {
            mainPane.setTop(screen2);
            switchButton.setText("Back to home");
        }
        else {
            mainPane.setTop(screen1);
            switchButton.setText("View controls");
        }
    }


    private void switchLeaderboard() {
        if(mainPane.getTop() == screen1){
            mainPane.setTop(screen3);
            leaderButton.setText("Back to home");
        } else {
            mainPane.setTop(screen1);
            leaderButton.setText("View Leaderboard");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

