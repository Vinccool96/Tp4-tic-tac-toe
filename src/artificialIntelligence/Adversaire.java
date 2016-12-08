package artificialIntelligence;

import java.util.ArrayList;

import modele.Emplacement;
import modele.TicTacToe;

public class Adversaire {

	protected TicTacToe tableDeJeu;
	protected ArrayList<Emplacement> emplacementsVides;

	public Adversaire(TicTacToe tableDeJeu) {
		this.tableDeJeu = tableDeJeu;
	}

	public void jouer(TicTacToe ticTacToe) {
	}

	protected void updateListe() {
		emplacementsVides = tableDeJeu.getListesEmplacementVides();
	}

}
