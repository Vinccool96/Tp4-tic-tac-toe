package artificialIntelligence;

import modele.Emplacement;
import modele.TicTacToe;

public class AI extends Adversaire {

	private MinimaxFinder finder;

	public AI(TicTacToe tableDeJeu) {
		super(tableDeJeu);
	}

	@Override
	public void jouer(TicTacToe nouvelleTableDeJeu) {
		finder = new MinimaxFinder(nouvelleTableDeJeu);
		Emplacement move = finder.returnNextMove();
		if (move != null) {
			tableDeJeu.getTable()[move.getX()][move.getY()] = 1;
		}
	}

}
