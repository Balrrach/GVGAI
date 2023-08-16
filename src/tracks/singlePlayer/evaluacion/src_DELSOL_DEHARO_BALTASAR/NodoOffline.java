package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


public class NodoOffline extends Nodo
{
	final int g;
	final int h;
	final public int coste;
	static private CostComparatorOffline comparadorOffline = new CostComparatorOffline();

	public NodoOffline(Coordenadas _coordenadas)
	{
		super(_coordenadas);
		g = 0;
		h = calculateH();
		coste = g + h;
	}

	public NodoOffline(Coordenadas _coordenadas, Nodo _parent, Types.ACTIONS _parentAction)
	{
		super(_coordenadas, _parent, _parentAction);
		g = ((NodoOffline)_parent).getG() + 1;
		h = calculateH();
		coste = g + h;
	}


	// Calculo de la distancia Manhattan
	private int calculateH()
	{
		return (int)(Math.abs(coordenadas.x - Agente.mapa.portal.x) + Math.abs(coordenadas.y - Agente.mapa.portal.y));
	}

	public int getG() { return g; }
	public int getH() { return h; }


	// Comentado en Nodo
	public ArrayList<NodoOffline> getSucesores() 
	{
		ArrayList<NodoOffline> vecinos = new ArrayList<NodoOffline>();
		var coordenadasVecino = new Coordenadas();

		coordenadasVecino = new Coordenadas(coordenadas.x, coordenadas.y + 1);
		if (coordenadasVecino.y <= Agente.mapa.limits.y) {
			if (validPos(coordenadasVecino))
			{
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
					}
				}
				else
				{
					vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
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
						vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
					}
				}
				else
				{
					vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
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
						vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
					}
				}
				else
				{
					vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
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
						vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
					}
				}
				else
				{
					vecinos.add(new NodoOffline(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
				}
			}
		}

		Collections.sort(vecinos, comparadorOffline);
		return vecinos;
	}
}

