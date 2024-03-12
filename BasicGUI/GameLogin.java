package BasicGUI;

import application.Homepage;
import application.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class    GameLogin {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public List<Player> playerlist = new ArrayList<>();
    TextField userText;
    public Player player;

    public final File textFile = new File("Resources/player.txt");

    private GameLogin() {
    }

    private static GameLogin instance = new GameLogin();

    public static GameLogin getInstance() {
        return instance;
    }

    public void load(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        anchorPane.setStyle("-fx-background-color: black;");

        // Create the title label
        Label titleLabel = new Label("WELCOME TO THE GAME");
        titleLabel.setStyle("-fx-font-size: " + FontSize.Large.getFontSize() + "px; -fx-font-family: 'Impact'; -fx-text-fill: white;");

        // Create the user TextField
        userText = new TextField();
        userText.setStyle("-fx-background-color:white;");
        userText.setFont(new Font(20));

        // Create the user lable
        Label userLabel = new Label("Input your username");
        userLabel.setStyle("-fx-font-size: " + FontSize.SMALL.getFontSize() + "px; -fx-font-family: 'Impact';-fx-text-fill: white; ");


        // Create the login button
        Button loginButton = new Button("login");
        loginButton.setStyle("-fx-font-size: " + FontSize.MEDIUM.getFontSize() + "px; -fx-background-color: transparent; -fx-text-fill: white;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-font-size: " + FontSize.MEDIUM.getFontSize() + "px; -fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-font-size: " + FontSize.MEDIUM.getFontSize() + "px; -fx-background-color: transparent; -fx-text-fill: white;"));
        loginButton.setOnAction(e -> {
            try {
                player = readPlayerFile();
                Homepage.getInstance().load(stage);     // switch to the homepage
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        anchorPane.getChildren().addAll(titleLabel, userLabel, userText, loginButton);
        AnchorPane.setLeftAnchor(titleLabel, 400.0);
        AnchorPane.setTopAnchor(titleLabel, 100.0);
        AnchorPane.setLeftAnchor(userLabel, 540.0);
        AnchorPane.setTopAnchor(userLabel, 300.0);
        AnchorPane.setLeftAnchor(userText, 530.0);
        AnchorPane.setTopAnchor(userText, 380.0);
        AnchorPane.setLeftAnchor(loginButton, 580.0);
        AnchorPane.setTopAnchor(loginButton, 460.0);

        // Create the scene
        Scene scene = new Scene(anchorPane, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        // Set the stage
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public Player readPlayerFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        Player player = null;
        List<Player> list = null;
        String playName = userText.getText().trim();
        if (textFile.exists()) {
            ois = new ObjectInputStream(new FileInputStream(textFile));
            list = (List<Player>) ois.readObject();
            ois.close();
            if ((list != null) && (!list.isEmpty())) {
                playerlist.clear();
                for (Player player1 : list) {
                    playerlist.add(player1);
                }
            }
        }

        if ((playerlist != null) && (!playerlist.isEmpty()))
            for (Player player1 : playerlist) {
                if (player1.getName().equals(playName)) {
                    player = player1;
                    break;
                }
            }

        if (player == null) {
            player = new Player(playName);
            playerlist.add(player);
        }
        return player;
    }

    public void wirtetxt(List<Player> list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textFile));
        oos.writeObject(list);
        oos.close();
    }
}
