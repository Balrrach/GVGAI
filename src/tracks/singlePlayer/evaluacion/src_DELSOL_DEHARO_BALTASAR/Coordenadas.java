package tracks.singlePlayer.myController;

import java.util.Objects;

import tools.Vector2d;


// Clase que abstrae a Vector2d y que implementa el método hashCode que permite
// usar la clase como clave en una tabla hash
public class Coordenadas extends Vector2d
{
	public Coordenadas() {
		super();
	}

	public Coordenadas(double x, double y) {
		super(x, y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
