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


public class AgenteBFS extends AgenteOffline
{

	public AgenteBFS(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
	}


	public Stack<Types.ACTIONS> offlineSearch()
	{
		Nodo inicial = new Nodo(Agente.mapa.getAvatar());
		Nodo solucion = inicial;
		Queue<Nodo> cola = new LinkedList<>();
		HashMap<Coordenadas, Boolean> visitados = new HashMap<Coordenadas, Boolean>();

		cola.add(inicial);
		visitados.put(inicial.coordenadas, true);

		// Mientras queden nodos en la cola
		while(!cola.isEmpty())
		{
			// Se toma como nodo actual la cabeza de la cola
			var nodoActual = cola.remove();

			// Si el nodo actual es objetivo el algoritmo ha terminado
			expandedNodes += 1;
			if (nodoActual.coordenadas.equals(Agente.mapa.portal)) { solucion = nodoActual; break; }

			// Se expande del nodo actual
			for (Nodo nodoVecino : nodoActual.getVecinos())
			{
				// Si el vecino no ha sido visitado se marca como visitado y se añade a la cola
				if (!visitados.containsKey(nodoVecino.coordenadas))
				{
					visitados.put(nodoVecino.coordenadas, true);
					cola.add(nodoVecino);
				}
			}
		}

		// Se actualiza el consumo de memoria
		memoryConsumption = visitados.size();

		return buildActionList(solucion);
	}
}

