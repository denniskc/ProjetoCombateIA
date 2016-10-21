package mainGame;

import java.awt.Color;

import ia.InterfaceIA;

public class UnidadeLanceiro extends Unidade {

	public UnidadeLanceiro(int x, int y, int oTime, Color color, InterfaceIA ia) {
		super(x, y, oTime, color, ia);
		tipo = TIPO_LANCEIRO;
		life = 100;
		dano = 8;
		vel  = 40;
		raioAtaque = 64;
	}

}
