package application;

import controleur.ControleurTicTacToe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TicTacToeMainApp extends Application {
	private BorderPane root = null;
	private Stage stage = null;
	
	private ControleurTicTacToe controleurTicTacToe;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/VueTicTacToe.fxml"));
		root = loader.load();
		controleurTicTacToe=loader.getController();

		Scene scene = new Scene(root);
		stage=primaryStage;
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
