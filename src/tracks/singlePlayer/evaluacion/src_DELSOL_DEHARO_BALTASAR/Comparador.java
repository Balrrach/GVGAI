package tracks.singlePlayer.myController;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

import java.util.Comparator;
import java.util.Map;


// Compara dos nodos en función del orden preestablecido de expansión
class Comparador implements Comparator<Nodo> {
	public int compare (Nodo n1, Nodo n2) {
		if (n1.getParentAction() == Types.ACTIONS.ACTION_UP)
			return -1;
		else if (n2.getParentAction() == Types.ACTIONS.ACTION_UP)
			return 1;

		else if (n1.getParentAction() == Types.ACTIONS.ACTION_DOWN)
			return -1;
		else if (n2.getParentAction() == Types.ACTIONS.ACTION_DOWN)
			return 1;

		else if (n1.getParentAction() == Types.ACTIONS.ACTION_LEFT)
			return -1;
		else if (n2.getParentAction() == Types.ACTIONS.ACTION_LEFT)
			return 1;

		else
			return -1;
	}
}

