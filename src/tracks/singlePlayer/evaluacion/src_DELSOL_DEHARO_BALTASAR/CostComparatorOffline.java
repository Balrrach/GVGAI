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
import tools.Vector2d;


// Compara dos NodoOffline en función del coste total, después del valor de g y
// por último del orden preestablecido de expansión
class CostComparatorOffline implements Comparator<NodoOffline> {
	public int compare (NodoOffline n1, NodoOffline n2) {
		int coste = (n1.coste) - (n2.coste);
		if(coste != 0)
			return coste;
		else
		{
			if (n1.getG() - n2.getG() != 0)
				return n1.getG() - n2.getG();

			else if (n1.getParentAction() == Types.ACTIONS.ACTION_UP)
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
}

