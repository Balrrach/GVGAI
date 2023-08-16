package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;


public class Nodo
{
	final public Coordenadas coordenadas;
	final public Nodo parent;
	final public Types.ACTIONS parentAction;

	public Nodo(Coordenadas _coordenadas)
	{
		coordenadas = _coordenadas;
		parent = null;
		parentAction = null;
	}

	public Nodo(Coordenadas _coordenadas, Nodo _parent)
	{
		coordenadas = _coordenadas;
		parent = _parent;
		parentAction = null;
	}

	public Nodo(Coordenadas _coordenadas, Nodo _parent, Types.ACTIONS _parentAction)
	{
		coordenadas = _coordenadas;
		parent = _parent;
		parentAction = _parentAction;
	}


	// Devuelve la acción asociada al padre del nodo
	public Types.ACTIONS getParentAction() { return parentAction; }

	// Devuelve los vecinos del nodo correctamente ordenados
	public ArrayList<Nodo> getVecinos() 
	{
		ArrayList<Nodo> vecinos = new ArrayList<Nodo>();
		var coordenadasVecino = new Coordenadas();

		// Se toman las coordenadas del vecino a considerar
		coordenadasVecino = new Coordenadas(coordenadas.x, coordenadas.y - 1);
		// Se comprueba que las coordenadas no están fuera de los los límites del mapa
		if (coordenadasVecino.y >= 0) {
			// Se comprueba que la coordenada actual es una posición válida
			if (validPos(coordenadasVecino))
			{
				// Si tiene padre se comprueba que las coordenadas del hijo no coinciden con las del padre
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
					}
				}
				else
				{
					vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_UP));
				}
			}
		}

		// Se toman las coordenadas del vecino a considerar
		coordenadasVecino = new Coordenadas(coordenadas.x, coordenadas.y + 1);
		// Se comprueba que las coordenadas no están fuera de los los límites del mapa
		if (coordenadasVecino.y <= Agente.mapa.limits.y) {
			// Se comprueba que la coordenada actual es una posición válida
			if (validPos(coordenadasVecino))
			{
				// Si tiene padre se comprueba que las coordenadas del hijo no coinciden con las del padre
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
					}
				}
				else
				{
					vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_DOWN));
				}
			}
		}

		// Se toman las coordenadas del vecino a considerar
		coordenadasVecino = new Coordenadas(coordenadas.x - 1, coordenadas.y);
		// Se comprueba que las coordenadas no están fuera de los los límites del mapa
		if (coordenadasVecino.x >= 0)
		{
			// Se comprueba que la coordenada actual es una posición válida
			if (validPos(coordenadasVecino))
			{
				// Si tiene padre se comprueba que las coordenadas del hijo no coinciden con las del padre
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
					{
						vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
					}
				}
				else
				{
					vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_LEFT));
				}
			}
		}

		// Se toman las coordenadas del vecino a considerar
		coordenadasVecino = new Coordenadas(coordenadas.x + 1, coordenadas.y);
		// Se comprueba que las coordenadas no están fuera de los los límites del mapa
		if (coordenadasVecino.x <= Agente.mapa.limits.x)
		{
			// Se comprueba que la coordenada actual es una posición válida
			if (validPos(coordenadasVecino))
			{
				// Si tiene padre se comprueba que las coordenadas del hijo no coinciden con las del padre
				if(parent != null)
				{
					if(coordenadasVecino != parent.coordenadas)
						vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
				}
				else
				{
					vecinos.add(new Nodo(coordenadasVecino, this, Types.ACTIONS.ACTION_RIGHT));
				}
			}
		}

		return vecinos;
	}


	// Comprueba que una posicion es válida comprobando su tipo de terreno asociado
	protected boolean validPos(Coordenadas pos)
	{
		if(Agente.mapa.grid[(int)pos.x][(int)pos.y] == tipoTerreno.TIERRA)
			return true;
		else
			return false;
	}
}
