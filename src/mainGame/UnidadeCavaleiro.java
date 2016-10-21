package mainGame;

import java.awt.Color;

import ia.InterfaceIA;

public class UnidadeCavaleiro extends Unidade{

	public UnidadeCavaleiro(int x, int y, int oTime, Color color, InterfaceIA ia) {
		super(x, y, oTime, color, ia);
		life = 200;
		dano = 15;
		vel  = 120;
		raioAtaque = 32;
	}

}
