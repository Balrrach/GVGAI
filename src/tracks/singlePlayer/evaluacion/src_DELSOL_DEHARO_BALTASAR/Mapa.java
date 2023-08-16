package tracks.singlePlayer.myController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;


// Enumerado con los tipos de casillas
enum tipoTerreno
{
	TIERRA, MURO, PINCHO
}

public class Mapa
{
	protected StateObservation stateObs;
	final protected Vector2d fescala;
	final protected Vector2d limits;
	protected Coordenadas avatar;
	final public Coordenadas portal;
	final protected tipoTerreno[][] grid;


	public Mapa(StateObservation _stateObs)
	{
		stateObs = _stateObs;
		fescala = calculateFscala();
		limits = calculateLimits();
		portal = calculatePortal();
		avatar = calculateAvatar();
		grid = calculateMap();
	}

	// Calcula el factor de escala
	private Vector2d calculateFscala()
	{
		// Se calcula el factor de escala entre mundos (pixeles -> grid)
		return new Vector2d(stateObs.getWorldDimension().width / stateObs.getObservationGrid().length, 
				stateObs.getWorldDimension().height / stateObs.getObservationGrid()[0].length);
	}

	// Calcula las coordenadas al portal mas cercano a la posicion inicial del avatar
	private Coordenadas calculatePortal()
	{
		// Se crea una lista de observaciones de portales, ordenada por cercania al avatar
		ArrayList<Observation>[] posiciones = stateObs.getPortalsPositions(stateObs.getAvatarPosition());

		// Se selecciona el portal más proximo como objetivo
		var portalVector2d = posiciones[0].get(0).position;
		var portal = new Coordenadas(portalVector2d.x, portalVector2d.y);
		portal.x = Math.floor(portal.x / fescala.x);
		portal.y = Math.floor(portal.y / fescala.y);

		return portal;
	}

	// Calcula las coordenadas en el grid de la posicion actual del avatar
	private Coordenadas calculateAvatar()
	{
		return new Coordenadas(stateObs.getAvatarPosition().x / fescala.x, 
				stateObs.getAvatarPosition().y / fescala.y);
	}

	// Calculo de los límites del grid
	private Vector2d calculateLimits()
	{
		Vector2d limits = new Vector2d();
		limits.x = stateObs.getObservationGrid().length;
		limits.y = stateObs.getObservationGrid()[0].length;

		return limits;
	}

	// Calculo del grid de terrenos
	private tipoTerreno[][] calculateMap()
	{
		var map = new tipoTerreno[(int)limits.x][(int)limits.y];

		// Se rellena de TIERRA
		for(var columna : map)
		{
			Arrays.fill(columna, tipoTerreno.TIERRA);
		}
		// Se ubican los MUROS
		for(Observation obs : stateObs.getImmovablePositions()[0])
		{
			var pos = obs.position;
			pos.x = Math.floor(pos.x / fescala.x);
			pos.y = Math.floor(pos.y / fescala.y);
			map[(int)pos.x][(int)pos.y] = tipoTerreno.MURO;
		}
		// Se ubican los PINCHOS
		for(Observation obs : stateObs.getImmovablePositions()[1])
		{
			var pos = obs.position;
			pos.x = Math.floor(pos.x / fescala.x);
			pos.y = Math.floor(pos.y / fescala.y);
			map[(int)pos.x][(int)pos.y] = tipoTerreno.PINCHO;
		}

		return map;
	}

	// Impresión del grid de terreno
	private void printMap()
	{
		System.out.println("Mapa Transpuesto");
		for(var columna : grid)
		{
			for(var casilla : columna)
			{
				if(casilla == tipoTerreno.MURO)
					System.out.print("MURO ");
				else if(casilla == tipoTerreno.PINCHO)
					System.out.print("PINCHO ");
				else if(casilla == tipoTerreno.TIERRA)
					System.out.print("TIERRA ");
				else 
					System.out.print("Null ");
			}
			System.out.println();
		}
	}

	// Devuelve la posición actual del avatar
	public Coordenadas getAvatar() { return avatar; }
}

