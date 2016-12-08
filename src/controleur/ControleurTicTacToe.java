package controleur;

import artificialIntelligence.AI;
import artificialIntelligence.Aleatoire;
import artificialIntelligence.Facile;
import interfaceTicTacToe.ModificationInterface;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import modele.Chronometre;
import modele.TicTacToe;

public class ControleurTicTacToe {

	@FXML
	private Button btnHG;
	@FXML
	private Button btnHC;
	@FXML
	private Button btnHD;
	@FXML
	private Button btnCG;
	@FXML
	private Button btnCC;
	@FXML
	private Button btnCD;
	@FXML
	private Button btnBG;
	@FXML
	private Button btnBC;
	@FXML
	private Button btnBD;
	@FXML
	private BorderPane root;
	@FXML
	private GridPane gridPane;
	@FXML
	private MenuBar menuBar;
	@FXML
	private MenuItem recommencerItem;
	@FXML
	private MenuItem quitterItem;
	@FXML
	private CheckMenuItem aleatoireCheckItem;
	@FXML
	private CheckMenuItem facileCheckItem;
	@FXML
	private CheckMenuItem aiCheckItem;
	@FXML
	private Button recommencerButton;
	@FXML
	private Button quitterButton;
	@FXML
	private Label lblDifficulte;
	@FXML
	private Label lblChronometre;

	private BooleanProperty isGameOver = new SimpleBooleanProperty(false);
	private TicTacToe tableDeJeu;

	private Chronometre chrono;
	Button[][] tabImageView;

	public ControleurTicTacToe() {

	}

	@FXML
	public void initialize() {

		recommencerItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tableDeJeu.reset();
			}

		});

		quitterItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		aleatoireCheckItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					facileCheckItem.setSelected(false);
					aiCheckItem.setSelected(false);
					tableDeJeu.setAdversaire(new Aleatoire(tableDeJeu));
				}
			}
		});

		facileCheckItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					aleatoireCheckItem.setSelected(false);
					aiCheckItem.setSelected(false);
					tableDeJeu.setAdversaire(new Facile(tableDeJeu));
				}
			}
		});

		aiCheckItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					facileCheckItem.setSelected(false);
					aleatoireCheckItem.setSelected(false);
					tableDeJeu.setAdversaire(new AI(tableDeJeu));
				}
			}
		});

		quitterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		recommencerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tableDeJeu.reset();
			}
		});

		chrono = new Chronometre(isGameOver, lblChronometre);
		Button[][] tab = { { btnHG, btnHC, btnHD }, { btnCG, btnCC, btnCD }, { btnBG, btnBC, btnBD } };

		tabImageView = tab;
		tableDeJeu = new TicTacToe(tabImageView, chrono, isGameOver, lblDifficulte);
		tableDeJeu.setAdversaire(new Aleatoire(tableDeJeu));
		ModificationInterface mI=new ModificationInterface(tab);
	}

	@FXML
	private void clickedHG() {
		tableDeJeu.placerMouvement(0, 0, 2);
	}

	@FXML
	private void clickedHC() {
		tableDeJeu.placerMouvement(0, 1, 2);
	}

	@FXML
	private void clickedHD() {
		tableDeJeu.placerMouvement(0, 2, 2);
	}

	@FXML
	private void clickedCG() {
		tableDeJeu.placerMouvement(1, 0, 2);
	}

	@FXML
	private void clickedCC() {
		tableDeJeu.placerMouvement(1, 1, 2);
	}

	@FXML
	private void clickedCD() {
		tableDeJeu.placerMouvement(1, 2, 2);
	}

	@FXML
	private void clickedBG() {
		tableDeJeu.placerMouvement(2, 0, 2);
	}

	@FXML
	private void clickedBC() {
		tableDeJeu.placerMouvement(2, 1, 2);
	}

	@FXML
	private void clickedBD() {
		tableDeJeu.placerMouvement(2, 2, 2);
	}

}
