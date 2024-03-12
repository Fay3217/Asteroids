package BasicGUI;

import application.Homepage;
import application.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javafx.application.Application.launch;

public class Scoreboard {
    public static void show(Stage stage, List<Player> playerList) throws IOException, ClassNotFoundException {


        ObservableList<Player> list = FXCollections.observableArrayList();
        for (Player player : playerList) {
            list.add(player);
        }


        TableView<Player> tableView = new TableView<>(list);

        TableColumn<Player, String> tc_mane = new TableColumn<>("name");
        tc_mane.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));


        TableColumn<Player, Number> tc_score = new TableColumn<>("score");
        tc_score.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Player, Number> tc_ranking = new TableColumn<>("ranking");
        tc_ranking.setCellValueFactory(new PropertyValueFactory<>("ranking"));


        tableView.getColumns().addAll(tc_mane, tc_score, tc_ranking);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //sorting
        list.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setRanking(i + 1);
        }


        tc_mane.setMinWidth(120);
        tc_mane.setResizable(false);
        tc_score.setMinWidth(120);
        tc_score.setResizable(false);
        tc_ranking.setMinWidth(120);
        tc_ranking.setResizable(false);
        tableView.setStyle("-fx-background-color:black;");
        tc_mane.setStyle("-fx-background-color:black;-fx-font-size:25px;-fx-alignment:center;-fx-text-fill: white;-fx-border-width:0px;");
        tc_ranking.setStyle("-fx-background-color:black;-fx-font-size:25px;-fx-alignment:center;-fx-text-fill: white;-fx-border-width:0px;");
        tc_score.setStyle("-fx-background-color:black;-fx-font-size:25px;-fx-alignment:center;-fx-text-fill: white;-fx-border-width:0px;");





        Label label =new Label("Leaderboard");
        label.setStyle("-fx-font-size: "+FontSize.Large.getFontSize()+"px; -fx-font-family: 'Impact';-fx-text-fill: white; ");

        Button exitButton = new Button("Homepage");
        exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;"));
        exitButton.setOnAction(e ->Homepage.getInstance().load(stage));


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: black;");

        anchorPane.getChildren().addAll(exitButton, label,tableView);


        AnchorPane.setTopAnchor(label, 40.0);
        AnchorPane.setLeftAnchor(label, 500.0);
        AnchorPane.setTopAnchor(tableView, 120.0);
        AnchorPane.setLeftAnchor(tableView, 450.0);
        AnchorPane.setTopAnchor(exitButton, 580.0);
        AnchorPane.setLeftAnchor(exitButton, 510.0);
        stage.setTitle("Asteroids!");
        stage.getScene().setFill(Color.BLACK);
        stage.getScene().setRoot(anchorPane);
    }
}
