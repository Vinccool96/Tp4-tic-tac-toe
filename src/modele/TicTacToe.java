package modele;

import java.util.ArrayList;
import java.util.Scanner;

import artificialIntelligence.AI;
import artificialIntelligence.Adversaire;
import artificialIntelligence.Aleatoire;
import artificialIntelligence.Facile;
import interfaceTicTacToe.ModificationInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;

public class TicTacToe {

	ArrayList<Emplacement> listeEmplacementsVides;
	Scanner scan = new Scanner(System.in);
	int[][] tableDeJeu = new int[3][3];
	private Button[][] tabButtons;
	private BooleanProperty hasPlayed = new SimpleBooleanProperty();
	private BooleanProperty isGameOver = new SimpleBooleanProperty();
	private Adversaire adversaire;
	private Chronometre chrono;
	private StringProperty difficulte = new SimpleStringProperty();
	private ModificationInterface mI;

	public TicTacToe(Button[][] tabButtons, Chronometre chrono, BooleanProperty isGameOver, Label lblDifficulte) {
		lblDifficulte.textProperty().bind(difficulte);
		getListesEmplacementVides();
		this.tabButtons = tabButtons;
		this.isGameOver.bindBidirectional(isGameOver);
		hasPlayed.addListener((new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				TicTacToe.this.isGameOver.set(isGameOver());
				if ((oldValue == false && newValue == true) && !TicTacToe.this.isGameOver.get()) {
					adversaire.jouer(TicTacToe.this);
					TicTacToe.this.isGameOver.set(isGameOver());
				}
				updateView();
			}
		}));

		mI = new ModificationInterface(tabButtons);

		this.chrono = chrono;

		this.isGameOver.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false && newValue == true) {
					ArrayList<Button> buttons = new ArrayList<>();

					if (tableDeJeu[0][0] == tableDeJeu[1][1] && tableDeJeu[1][1] == tableDeJeu[2][2]) {
						buttons.add(tabButtons[0][0]);
						buttons.add(tabButtons[1][1]);
						buttons.add(tabButtons[2][2]);
						mI.changerCouleurButtons(buttons);
					} else if (tableDeJeu[0][2] == tableDeJeu[1][1] && tableDeJeu[1][1] == tableDeJeu[2][0]) {
						buttons.add(tabButtons[0][2]);
						buttons.add(tabButtons[1][1]);
						buttons.add(tabButtons[2][0]);
						mI.changerCouleurButtons(buttons);
					} else {
						boolean opt1 = false;
						int intOpt1 = 0;
						for (; intOpt1 < 3; ++intOpt1) {
							if (tableDeJeu[intOpt1][0] == tableDeJeu[intOpt1][1]
									&& tableDeJeu[intOpt1][1] == tableDeJeu[intOpt1][2]) {
								opt1 = true;
								break;
							}
						}

						if (opt1) {
							buttons.add(tabButtons[intOpt1][0]);
							buttons.add(tabButtons[intOpt1][1]);
							buttons.add(tabButtons[intOpt1][2]);
							mI.changerCouleurButtons(buttons);
						} else {
							boolean opt2 = false;
							int intOpt2 = 0;
							for (; intOpt2 < 3; ++intOpt2) {
								if (tableDeJeu[0][intOpt2] == tableDeJeu[1][intOpt2]
										&& tableDeJeu[1][intOpt2] == tableDeJeu[2][intOpt2]) {
									opt2 = true;
									break;
								}
							}

							if (opt2) {
								buttons.add(tabButtons[0][intOpt2]);
								buttons.add(tabButtons[1][intOpt2]);
								buttons.add(tabButtons[2][intOpt2]);
								mI.changerCouleurButtons(buttons);
							}
						}
					}

					Alert alert;
					if (hasAIWon()) {
						alert = new Alert(AlertType.INFORMATION, "Just like I did to your mom and your sister last night", ButtonType.YES);
						alert.setHeaderText("You have been fucked like a sensless whore");
					} else if (hasPlayerWon()) {
						alert = new Alert(AlertType.ERROR, "You cheated", ButtonType.YES);
					} else {
						alert = new Alert(AlertType.WARNING, "Because my power level IS OVER 9000!!!!!!!!!!",
								ButtonType.OK);
						alert.setHeaderText("You tried to defeat me, but you couldn't");
					}
					alert.show();
					alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
						@Override
						public void handle(DialogEvent event) {
							reset();
						}
					});
				}
			}
		});
	}

	public void setAdversaire(Adversaire adversaire) {
		this.adversaire = adversaire;
		reset();
	}

	public boolean isGameOver() {
		// Game is over is someone has won, or tableDeJeu is full (draw)
		return (hasAIWon() || hasPlayerWon() || getListesEmplacementVides().isEmpty());
	}

	public boolean hasAIWon() {
		boolean so = false;
		if ((tableDeJeu[0][0] == tableDeJeu[1][1] && tableDeJeu[0][0] == tableDeJeu[2][2] && tableDeJeu[0][0] == 1)
				|| (tableDeJeu[0][2] == tableDeJeu[1][1] && tableDeJeu[0][2] == tableDeJeu[2][0]
						&& tableDeJeu[0][2] == 1)) {
			so = true;
		}
		if (!so) {
			for (int i = 0; i < 3; ++i) {
				if (((tableDeJeu[i][0] == tableDeJeu[i][1] && tableDeJeu[i][0] == tableDeJeu[i][2]
						&& tableDeJeu[i][0] == 1)
						|| (tableDeJeu[0][i] == tableDeJeu[1][i] && tableDeJeu[0][i] == tableDeJeu[2][i]
								&& tableDeJeu[0][i] == 1))) {
					so = true;
					break;
				}
			}
		}
		return so;
	}

	public boolean hasPlayerWon() {
		boolean so = false;
		if ((tableDeJeu[0][0] == tableDeJeu[1][1] && tableDeJeu[0][0] == tableDeJeu[2][2] && tableDeJeu[0][0] == 2)
				|| (tableDeJeu[0][2] == tableDeJeu[1][1] && tableDeJeu[0][2] == tableDeJeu[2][0]
						&& tableDeJeu[0][2] == 2)) {
			so = true;
		}
		if (!so) {
			for (int i = 0; i < 3; ++i) {
				if ((tableDeJeu[i][0] == tableDeJeu[i][1] && tableDeJeu[i][0] == tableDeJeu[i][2]
						&& tableDeJeu[i][0] == 2)
						|| (tableDeJeu[0][i] == tableDeJeu[1][i] && tableDeJeu[0][i] == tableDeJeu[2][i]
								&& tableDeJeu[0][i] == 2)) {
					so = true;
					break;
				}
			}
		}
		return so;
	}

	public ArrayList<Emplacement> getListesEmplacementVides() {
		listeEmplacementsVides = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (tableDeJeu[i][j] == 0) {
					listeEmplacementsVides.add(new Emplacement(i, j));
				}
			}
		}
		return listeEmplacementsVides;
	}

	public int[][] getTable() {
		return tableDeJeu;
	}

	public void placerMouvement(Emplacement emplacement, int joueur) {
		if (!isGameOver.get()) {
			if (getTable()[emplacement.getX()][emplacement.getY()] == 0) {
				getTable()[emplacement.getX()][emplacement.getY()] = joueur;
				if (joueur == 2) {
					hasPlayed.setValue(true);
				}
			} else {
				System.out.println("You stupid");
			}
		} else {
			System.out.println("This is the end");
		}
		updateView();
	}

	public void placerMouvement(int x, int y, int joueur) {
		if (!isGameOver.get()) {
			if (getTable()[x][y] == 0) {
				getTable()[x][y] = joueur;
				if (joueur == 2) {
					hasPlayed.setValue(true);
				}
			} else {
				System.out.println("You stupid");
			}
		} else {
			System.out.println("This is the end");
		}
		updateView();
	}

	public void placerMouvementMinimax(Emplacement emplacement, int joueur) {
		if (!isGameOver.get()) {
			if (getTable()[emplacement.getX()][emplacement.getY()] == 0) {
				getTable()[emplacement.getX()][emplacement.getY()] = joueur;
				if (joueur == 2) {
					hasPlayed.setValue(true);
				}
			} else {
				System.out.println("You stupid");
			}
		} else {
			System.out.println("This is the end");
		}
	}

	public void placerMouvementMinimax(int x, int y, int joueur) {
		if (!isGameOver.get()) {
			if (getTable()[x][y] == 0) {
				getTable()[x][y] = joueur;
				if (joueur == 2) {
					hasPlayed.setValue(true);
				}
			} else {
				System.out.println("You stupid");
			}
		} else {
			System.out.println("This is the end");
		}
	}

	public void resetTable() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				tableDeJeu[i][j] = 0;
			}
		}
		updateView();
	}

	private void updateView() {
		ModificationInterface.modifierImages(tabButtons, getTable());
		hasPlayed.set(false);
	}

	public void reset() {

		switch (adversaire.getClass().getName()) {
		case "artificialIntelligence.Aleatoire":
			difficulte.set("Aléatoire!".toUpperCase());
			adversaire = new Aleatoire(this);
			break;
		case "artificialIntelligence.Facile":
			difficulte.set("Facile!".toUpperCase());
			adversaire = new Facile(this);
			break;
		case "artificialIntelligence.AI":
			difficulte.set("AI!");
			adversaire = new AI(this);
			break;
		default:
			break;
		}

		isGameOver.set(false);
		resetTable();
		updateView();
		chrono.restart();
	}
}
