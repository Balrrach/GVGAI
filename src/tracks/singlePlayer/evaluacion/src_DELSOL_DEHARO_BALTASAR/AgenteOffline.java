package tracks.singlePlayer.myController;

import java.util.Stack;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

public abstract class AgenteOffline extends Agente
{
	boolean firstTick;


	public AgenteOffline(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
		firstTick = true;
	}


	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		// Si es la primera vez que se llama a la función act se calcula
		// la pila de acciones
		if(firstTick)
		{
			firstTick = false;

			long tInicio = System.nanoTime();
			listaAcciones = offlineSearch();
			long tFin = System.nanoTime();
			executionTime = (tFin - tInicio)/1000000;
			routeSize = listaAcciones.size();

			printMetricas();

			return listaAcciones.pop();
		}

		// Si no es la primera vez se devuelve la acción en el tope de la
		// pila de acciones
		else
		{
			return listaAcciones.pop();
		}
	}

	// Método de búsqueda que caracteriza al algoritmo offline
	abstract public Stack<Types.ACTIONS> offlineSearch();
}

