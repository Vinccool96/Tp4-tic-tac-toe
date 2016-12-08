package artificialIntelligence;

import java.util.Random;

import modele.Emplacement;
import modele.TicTacToe;

public class Aleatoire extends Adversaire {

	Random randomizer = new Random();

	public Aleatoire(TicTacToe tableDeJeu) {
		super(tableDeJeu);
		emplacementsVides = tableDeJeu.getListesEmplacementVides();
	}

	private Emplacement emplacementJoue() {
		return this.emplacementsVides.get(randomizer.nextInt(emplacementsVides.size()));
	}

	public void jouer(TicTacToe ticTacToe) {
		this.updateListe();
		Emplacement emplacement = emplacementJoue();
		tableDeJeu.getTable()[emplacement.getX()][emplacement.getY()] = 1;
	}

}
