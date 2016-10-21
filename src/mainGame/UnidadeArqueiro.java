package mainGame;

import java.awt.Color;

import ia.InterfaceIA;

public class UnidadeArqueiro extends Unidade{

	public UnidadeArqueiro(int x, int y, int oTime, Color color, InterfaceIA ia) {
		super(x, y, oTime, color, ia);
		life = 60;
		dano = 5;
		vel  = 30;
		raioAtaque = 256;
	}

}
