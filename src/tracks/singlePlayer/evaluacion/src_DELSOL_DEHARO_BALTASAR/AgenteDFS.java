package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Stack;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


public class AgenteDFS extends AgenteOffline
{
	Nodo inicial;
	Coordenadas objetivo;
	HashMap<Coordenadas, Boolean> visitados;
	Nodo solucion;

	public AgenteDFS(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
	}


	public Stack<Types.ACTIONS> offlineSearch()
	{
		inicial  = new Nodo(Agente.mapa.getAvatar());
		objetivo = Agente.mapa.portal;
		visitados = new HashMap<Coordenadas, Boolean>();
		visitados.put(inicial.coordenadas, true);

		DFS_Search(inicial);

		memoryConsumption = visitados.size();
		return buildActionList(solucion);
	}

	public boolean DFS_Search(Nodo actual)
	{
		// Si el nodo actual es objetivo el algoritmo ha terminado
		expandedNodes += 1;
		if (actual.coordenadas.equals(Agente.mapa.portal)) { solucion = actual; return true; }

		// Se expande del nodo actual
		for (Nodo nodoVecino : actual.getVecinos())
		{
			// Si el vecino no ha sido visitado se marca como visitado y se llama de forma
			// recursiva la función tomando como parámetro dicho nodo
			if (!visitados.containsKey(nodoVecino.coordenadas))
			{
				visitados.put(nodoVecino.coordenadas, true);
				if (DFS_Search(nodoVecino) == true)
					return true;
			}
		}

		return false;
	}
}

