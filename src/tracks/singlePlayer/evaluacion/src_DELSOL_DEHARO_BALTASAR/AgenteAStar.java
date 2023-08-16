package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map;
import java.util.PriorityQueue;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


public class AgenteAStar extends AgenteOffline
{
	public AgenteAStar(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
	}

	public Stack<Types.ACTIONS> offlineSearch()
	{
		HashMap<Coordenadas, NodoOffline> abiertosHMap = new HashMap<>();
		PriorityQueue<NodoOffline> abiertosPQueue = new PriorityQueue<NodoOffline>(new CostComparatorOffline());
		HashMap<Coordenadas, NodoOffline> cerrados = new HashMap<>();
		var objetivo = Agente.mapa.portal;
		var inicial = new NodoOffline(Agente.mapa.getAvatar());

		abiertosHMap.put(inicial.coordenadas, inicial);
		abiertosPQueue.add(inicial);
		NodoOffline solucion = inicial;

		while(true)
		{
			// Se selecciona el nodo con menor coste
			Coordenadas claveCandidato = abiertosPQueue.poll().coordenadas;
			NodoOffline candidato = abiertosHMap.get(claveCandidato);
			abiertosHMap.remove(claveCandidato);

			// Si el nodo actual es objetivo el algoritmo ha terminado
			expandedNodes += 1;
			if (candidato.coordenadas.equals(Agente.mapa.portal)) { solucion = candidato; break; }
			

			// Se expande del nodo actual
			var candidatos = candidato.getSucesores();
			for(var sucesor : candidatos)
			{
				Coordenadas claveSucesor = sucesor.coordenadas;
				boolean sucesorEnCerrados = cerrados.containsKey(claveSucesor);
				boolean sucesorEnAbiertos = abiertosHMap.containsKey(claveSucesor);

				// Si el sucesor esta en cerrados con un coste mayor es sustituido
				boolean completo = false;
				if(sucesorEnCerrados)
				{
					if(sucesor.coste < cerrados.get(claveSucesor).coste)
					{
						abiertosPQueue.remove(cerrados.get(claveSucesor));
						abiertosPQueue.add(sucesor);
						abiertosHMap.put(claveSucesor, sucesor);
						cerrados.remove(claveSucesor);
						completo = true;
					}
				}

				// Si no ocurre lo anterior y el nodo no esta ni en abiertos
				// ni en cerrados se añade a abiertos
				if(!completo && !sucesorEnCerrados && !sucesorEnAbiertos)
				{
					abiertosHMap.put(claveSucesor, sucesor);
					abiertosPQueue.add(sucesor);
				}

				// Si no ocurre lo anterior y el nodo ya está en abiertos con un
				// coste mayor es sustituido
				else if(sucesorEnAbiertos)
				{
					if(sucesor.coste < abiertosHMap.get(claveSucesor).coste)
					{
						abiertosPQueue.remove(abiertosHMap.get(claveSucesor));
						abiertosPQueue.add(sucesor);
						abiertosHMap.put(claveSucesor, sucesor);
					}
				}
			}

			// Se añade el candidato a la lista de cerrados
			cerrados.put(candidato.coordenadas, candidato);

			// En caso necesario se actualiza el consumo de memoria
			int currentConsumption = abiertosPQueue.size() + cerrados.size();
			if( currentConsumption > memoryConsumption)
				memoryConsumption = currentConsumption;
		}

		return buildActionList(solucion);
	}
}
