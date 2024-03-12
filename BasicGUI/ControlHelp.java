package BasicGUI;

import application.Homepage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ControlHelp {
    public static void show(Stage stage){
        AnchorPane anchorPane=new AnchorPane();
        Button exitButton = new Button("Homepage");
        exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;"));
        exitButton.setOnAction(e -> Homepage.getInstance().load(stage));
        exitButton.setLayoutX(550);
        exitButton.setLayoutY(620);

        Label titleLabel = new Label("Control Help Information");
        titleLabel.setStyle("-fx-font-size: "+FontSize.Large.getFontSize()+"px; -fx-font-family: 'Impact'; -fx-text-fill: white;");
        titleLabel.setLayoutX(400);
        titleLabel.setLayoutY(60);

        Label textLable1 = new Label("↑: Control Speed");
        textLable1.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        textLable1.setLayoutX(500);
        textLable1.setLayoutY(150);

        Label textLable2 = new Label("←→: Control Direction");
        textLable2.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        textLable2.setLayoutX(500);
        textLable2.setLayoutY(250);

        Label textLable3 = new Label("Space: Projectile");
        textLable3.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        textLable3.setLayoutX(500);
        textLable3.setLayoutY(350);

        Label textLable4 = new Label("H: Hyperjump");
        textLable4.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        textLable4.setLayoutX(500);
        textLable4.setLayoutY(450);

        Label textLable5 = new Label("E: Exit during the game");
        textLable5.setStyle("-fx-font-size: "+FontSize.MEDIUM.getFontSize()+"px; -fx-background-color: transparent; -fx-text-fill: white;");
        textLable5.setLayoutX(500);
        textLable5.setLayoutY(550);

        anchorPane.getChildren().addAll(exitButton,titleLabel,textLable1, textLable2,textLable3,textLable4,textLable5);
        anchorPane.setStyle("-fx-background-color: black;");
        stage.setTitle("Asteroids!");
        stage.getScene().setRoot(anchorPane);

    }
}
