package BasicGUI;

import application.Homepage;
import application.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GameOver {
    public static void start(Stage stage, int point) {
        Pane pane = new Pane();
        //Background colour
        BackgroundFill bgf = new BackgroundFill(Paint.valueOf("BLACK"), new CornerRadii(0), new Insets(0));
        Background background = new Background(bgf);
        pane.setBackground(background);
        //Game over title
        Label label = new Label("GAME OVER");
        label.setLayoutX(420);
        label.setLayoutY(50);
        label.setFont(new Font(FontSize.Large.getFontSize()));
        label.setTextFill(Color.valueOf("WHITE"));
        //show player's score
        Text score = new Text(470, 250, "YOUR SCORE:  " + point);
        score.setFill(Color.valueOf("WHITE"));
        score.setFont(new Font(FontSize.MEDIUM.getFontSize()));
        //show player's history highest rank
        //update player's highest score
        Text rank;
        boolean isNewHighScore = false;
        Player currentPlayer = GameLogin.getInstance().player;
        if (currentPlayer.getScore() > point) {   // lower than history high score
            rank = new Text(470, 370, "YOUR HIGHEST RANKING:  " + GameLogin.getInstance().player.getRanking());
            rank.setFill(Color.valueOf("WHITE"));
            rank.setFont(new Font(FontSize.MEDIUM.getFontSize()));
        } else {
            // new high score, update the ranking info
            isNewHighScore = true;
            List<Player> playerlist = GameLogin.getInstance().playerlist;
            playerlist.sort((o1, o2) -> o2.getScore() - o1.getScore());
            for (int i = 0; i < playerlist.size(); i++) {
                playerlist.get(i).setRanking(i + 1);
            }
            // get the new ranking, write it into the file
            rank = new Text(470, 370, "YOUR HIGHEST RANKING:  " + GameLogin.getInstance().player.getRanking());
            rank.setFill(Color.valueOf("WHITE"));
            rank.setFont(new Font(FontSize.MEDIUM.getFontSize()));
        }


//        Text scoreNum = new Text(190, 250, "");
//        scoreNum.setFont(new Font(50));
//        scoreNum.setFill(Color.valueOf("WHITE"));
//        scoreNum.setText("Nan");  //get score from another method
//
        //if the score is a new high score, show the congrats
        Text congrats = new Text(100,350,"Congratulations!\nYou have achieved\n a new high score!");
        congrats.setFill(Color.valueOf("RED"));
        congrats.setFont(new Font(FontSize.SMALL.getFontSize()));

        Button exit_game = new Button("EXIT THE GAME");
        exit_game.setLayoutX(500);
        exit_game.setLayoutY(450);
        exit_game.setTextFill(Color.WHITE);
        exit_game.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: black; -fx-border-color: white; -fx-border-width: 0px;");
        exit_game.setOnMouseEntered(e -> exit_game.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: rgba(255, 255, 255, 0.2); -fx-border-color: white; -fx-border-width: 0px;"));
        exit_game.setOnMouseExited(e -> exit_game.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: black; -fx-border-color: white; -fx-border-width: 0px;"));
        exit_game.setOnAction(e -> System.exit(0));
        //Back to main menu button
        Button backToMenu = new Button("BACK TO HOMEPAGE");
        backToMenu.setLayoutX(450);
        backToMenu.setLayoutY(550);
        backToMenu.setTextFill(Color.valueOf("WHITE"));
        backToMenu.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: black; -fx-border-color: white; -fx-border-width: 0px;");
        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: rgba(255, 255, 255, 0.2); -fx-border-color: white; -fx-border-width: 0px;"));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px;-fx-background-color: black; -fx-border-color: white; -fx-border-width: 0px;"));
        //set on action
        backToMenu.setOnAction(e -> Homepage.getInstance().load(stage));

        pane.getChildren().addAll(label, backToMenu, score, rank, exit_game);
//        judge if it is a new high score
        if (isNewHighScore){
            pane.getChildren().add(congrats);
        }
        stage.setTitle("Asteroids!");
        stage.getScene().setRoot(pane);
    }
}
