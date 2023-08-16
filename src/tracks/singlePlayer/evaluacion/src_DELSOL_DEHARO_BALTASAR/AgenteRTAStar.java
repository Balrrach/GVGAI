package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import ontology.Types;


public class AgenteRTAStar extends Agente
{
	NodoOnline actual;

	// Comentado en Nodo
	public AgenteRTAStar(StateObservation _stateObs, ElapsedCpuTimer _elapsedTimer)
	{
		super(_stateObs, _elapsedTimer);
		actual = new NodoOnline(mapa.avatar);
	}


	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		routeSize += 1;
		long tInicio = System.nanoTime();

		// Se expande del nodo actual
		var listaSucesores = actual.getSucesores();
		// Para evitar ciclos se actualiza el coste de las casillas
		actualizarCoste(actual, listaSucesores);
		// Se selecciona la nueva acción como el mejor entre los nodos sucesores
		actual = listaSucesores.get(0);

		long tFin = System.nanoTime();
		executionTime += (tFin - tInicio);

		// Si el nodo actual es objetivo el algoritmo ha terminado
		if (actual.coordenadas.equals(mapa.portal))
		{
			expandedNodes = routeSize;
			memoryConsumption = actual.getExploredNodesNumber();
			executionTime = executionTime/1000000;
			printMetricas();
		}

		return actual.getParentAction();
	}

	// Función encargada de actualizar el coste del nodo actual
	private void actualizarCoste(NodoOnline actual, ArrayList<NodoOnline> sucesores)
	{
		// Se encuentra el segundo coste mínimo que sólo depende de H puesto que
		// todos los nodo tiene la misma distancia al nodo inicial g
		var segundoMinH = 0;
		if (sucesores.size() > 1)
			segundoMinH = sucesores.get(1).getH();
		else
			segundoMinH = sucesores.get(0).getH();

		// Se actualiza el coste h del nodo con dicho mínimo
		actual.updateH(segundoMinH);
	}
}

