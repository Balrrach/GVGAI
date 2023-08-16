package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


public class NodoOnline extends Nodo
{
	int g;
	int h;
	public int coste;
	static private HashMap<Coordenadas, Integer> learningRule = new HashMap<>();
	static private CostComparatorOnline comparadorOnline = new CostComparatorOnline();


	public NodoOnline(Coordenadas _coordenadas)
	{
		super(_coordenadas);
		g = 0;
		h = calculateH();
		coste = g + h;
	}

	public NodoOnline(Coordenadas _coordenadas, Nodo _parent, Types.ACTIONS _parentAction)
	{
		super(_coordenadas, _parent, _parentAction);
		g = ((NodoOnline)_parent).getG() + 1;
		h = calculateH();
		coste = g + h;
	}

	private int calculateH()
	{
		// Si no tiene un coste actualizado se calcula como la distancia Manhattan
		if (learningRule.containsKey(coordenadas))
			return learningRule.get(coordenadas);
		else
			return (int)(Math.abs(coordenadas.x - Agente.mapa.portal.x) + Math.abs(coordenadas.y - Agente.mapa.portal.y));
	}

	public void updateH(int nextHeuristicCost)
	{
		// Se actualiza h como el maximo entre h actual y h del segundo mejor vecino con el coste añadido de llegar haste el
		h = Math.max(h, 1 + nextHeuristicCost);
		// Se actualiza el coste asociado a la casilla
		learningRule.put(coordenadas, h);
	}

	public int getG() { return g; }
	public int getH() { return h; }
	public int getExploredNodesNumber() { return learningRule.size(); }


	// Comentado en Nodo
	public ArrayList<NodoOnline> getSucesores() 
	{
		ArrayList<NodoOnline> vecinos = new ArrayList<NodoOnline>();
		var coordenadasVecino = new Coordenadas();

		coordenadasVecino = new Coordenadas(coordenadas.x, coordenadas.y + 1);
		if (coordenadasVecino.y <= Agente.mapa.limits.y) {
			if (validPos(coordenadasVecino))
			{
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
					}
				}
				else
				{
					vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
				}
			}
		}

		coordenadasVecino = new Coordenadas(coordenadas.x, coordenadas.y - 1);
		if (coordenadasVecino.y >= 0) {
			if (validPos(coordenadasVecino))
			{
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
					}
				}
				else
				{
					vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
				}
			}
		}

		coordenadasVecino = new Coordenadas(coordenadas.x - 1, coordenadas.y);
		if (coordenadasVecino.x >= 0) {
			if (validPos(coordenadasVecino))
			{
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
					}
				}
				else
				{
					vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
				}
			}
		}

		coordenadasVecino = new Coordenadas(coordenadas.x + 1, coordenadas.y);
		if (coordenadasVecino.x <= Agente.mapa.limits.x) {
			if (validPos(coordenadasVecino))
			{
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
					}
				}
				else
				{
					vecinos.add(new NodoOnline(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
				}
			}
		}


		Collections.sort(vecinos, comparadorOnline);
		return vecinos;
	}
}


