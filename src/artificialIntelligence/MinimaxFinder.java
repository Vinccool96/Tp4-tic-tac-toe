package artificialIntelligence;

import java.util.List;

import modele.Emplacement;
import modele.TicTacToe;

public class MinimaxFinder {
	private TicTacToe ticTacToe;
	Emplacement mouvementOrdinateur;

	public MinimaxFinder(TicTacToe nouvelleTableDeJeu) {
		ticTacToe = nouvelleTableDeJeu;
	}

	public int minimax(int profondeur, int tour) {
		if (ticTacToe.hasAIWon())
			return +1;
		if (ticTacToe.hasPlayerWon())
			return -1;

		List<Emplacement> emplacementsVides = ticTacToe.getListesEmplacementVides();
		if (emplacementsVides.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < emplacementsVides.size(); ++i) {
			Emplacement emplacement = emplacementsVides.get(i);
			if (tour == 1) {
				ticTacToe.placerMouvementMinimax(emplacement, 1);
				int scoreCourant = minimax(profondeur + 1, 2);
				max = Math.max(scoreCourant, max);

				if (profondeur == 0) {

				}
				if (scoreCourant >= 0) {
					if (profondeur == 0)
						mouvementOrdinateur = emplacement;
				}
				if (scoreCourant == 1) {
					ticTacToe.getTable()[emplacement.getX()][emplacement.getY()] = 0;
					break;
				}
				if (i == emplacementsVides.size() - 1 && max < 0) {
					if (profondeur == 0)
						mouvementOrdinateur = emplacement;
				}
			} else if (tour == 2) {
				ticTacToe.placerMouvementMinimax(emplacement, 2);
				int currentScore = minimax(profondeur + 1, 1);
				min = Math.min(currentScore, min);
				if (min == -1) {
					ticTacToe.getTable()[emplacement.getX()][emplacement.getY()] = 0;
					break;
				}
			}
			ticTacToe.getTable()[emplacement.getX()][emplacement.getY()] = 0; // Reset
																			// this
																			// point
		}
		return tour == 1 ? max : min;
	}

	public Emplacement returnNextMove() {
		if (ticTacToe.isGameOver())
			return null;
		minimax(0, 1);
		return mouvementOrdinateur;
	}
}