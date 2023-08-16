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

// import java.util.Collections;

public abstract class Agente extends AbstractPlayer
{
	protected StateObservation stateObs;
	public static Mapa mapa;
	Stack<Types.ACTIONS> listaAcciones;

	long executionTime = 0;
	int routeSize = 0;
	int expandedNodes = 0;
	int memoryConsumption = 0;


	public Agente(StateObservation _stateObs, ElapsedCpuTimer _elapsedTimer)
	{
		stateObs = _stateObs;
		mapa = new Mapa(stateObs);
	}


	// Construcción de la lista de acciones asociadas a un nodo
	public Stack<Types.ACTIONS> buildActionList(Nodo solucion)
	{
		Stack<Types.ACTIONS> colaAcciones = new Stack<>();
		ArrayList<Types.ACTIONS> result = new ArrayList<Types.ACTIONS>();
		var nodoActual = solucion;
		while(nodoActual.parentAction != null)
		{
			colaAcciones.add(nodoActual.parentAction);
			nodoActual = nodoActual.parent;
		}

		if (colaAcciones.isEmpty()) { System.out.println("Error: La lista de acciones es vacia"); }
		return colaAcciones;
	}

	// Impresión de las métricas
	protected void printMetricas()
	{
		System.out.println("Tiempo de ejecucion: " + executionTime);
		System.out.println("Tamaño de la ruta: " + routeSize);
		System.out.println("Número de nodos expandidos: " + expandedNodes);
		System.out.println("Consumo de memoria: " + memoryConsumption);
	}
}

