package mainGame;

import java.awt.Color;

import ia.InterfaceIA;

public class UnidadeSoldado extends Unidade{

	public UnidadeSoldado(int x, int y, int oTime, Color color, InterfaceIA ia) {
		super(x, y, oTime, color, ia);
		// TODO Auto-generated constructor stub
		tipo = TIPO_SOLDADO;
		life = 100;
		dano = 10;
		vel  = 40;
		raioAtaque = 32;
	}

}
