package application;
	
import BasicGUI.GameLogin;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		GameLogin.getInstance().load(primaryStage);
			}
	
	public static void main(String[] args) {
		launch(args);
	}
}
