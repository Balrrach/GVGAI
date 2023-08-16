package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.LinkedHashMap;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


enum Resultado
{
	ENCONTRADO, INFINITO
}

public class AgenteIDAStar extends AgenteOffline
{
	NodoOffline solucion;

	public AgenteIDAStar(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
	}


	public Stack<Types.ACTIONS> offlineSearch()
	{
		LinkedHashMap<Coordenadas, NodoOffline> ruta = new LinkedHashMap<>();
		var inicial = new NodoOffline(Agente.mapa.getAvatar());
		var solucion = inicial;
		var cota = inicial.getG();
		ruta.put(inicial.coordenadas, inicial);

		while(true)
		{
			var resultado = IDA_search(ruta, 0, cota);

			// Si el resultado es un numero negativo se ha encontrado solución
			if (resultado < 0)
			{
				Coordenadas lastElementKey = new ArrayList<>(ruta.keySet()).get(ruta.size() - 1);
				solucion = ruta.get(lastElementKey);
				var listaAcciones = buildActionList(solucion);
				memoryConsumption = listaAcciones.size();
				return listaAcciones;
			}

			// Si el resultado es infinito se ha terminado el algoritmo y no hay solucion
			if (resultado == Double.POSITIVE_INFINITY)
				throw new ArithmeticException("No se ha encontrado solución");

			// Se actualiza la cota
			cota = resultado;
		}
	}

	public int IDA_search(LinkedHashMap<Coordenadas, NodoOffline> ruta, int g, int cota)
	{
		// Se toma como nodo actual el último elemento de la ruta
		Coordenadas lastElementKey = new ArrayList<>(ruta.keySet()).get(ruta.size() - 1);
		var actual = ruta.get(lastElementKey);

		// Se calcula el coste del nodo y, si sobrepasa la cota, se devuelve y no se
		// continua explorando la ruta actual
		int f = g + actual.getH();
		if (f > cota) { return f; }

		// Si el nodo actual es objetivo el algoritmo ha terminado
		expandedNodes += 1;
		if (actual.coordenadas.equals(Agente.mapa.portal)) { solucion = actual; return -1; }

		// Se expande del nodo actual
		var min = Double.POSITIVE_INFINITY;
		var sucesores = actual.getSucesores();
		for(var sucesor : sucesores)
		{
			// Si el nodo no ha sido visitado en la ruta actual 
			if (!ruta.containsKey(sucesor.coordenadas))
			{
				// Se incluye a la ruta y se llama a la función de forma recursiva sobre
				// la nueva ruta
				ruta.put(sucesor.coordenadas, sucesor);
				var t = IDA_search(ruta, g+1, cota);

				// Si devuelve negativo es porque se ha encontrado solución y se propaga
				if (t < 0)
					return t;

				// Se actualiza el coste mínimo
				if (t < min)
					min = t;

				// Se elimina el nodo de la ruta para probar con otro vecino
				ruta.remove(sucesor.coordenadas);
			}
		}

		return (int)min;
	}
}

