package mainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import ia.InterfaceIA;
import javafx.scene.transform.Affine;

public class UnidadeArqueiro extends Unidade{

	Polygon p;
	
	public UnidadeArqueiro(int x, int y, int oTime, Color color, InterfaceIA ia) {
		super(x, y, oTime, color, ia);
		tipo = TIPO_ARQUEIRO;
		life = 60;
		dano = 5;
		vel  = 30;
		raioAtaque = 256;
		
		p = new Polygon();
		p.addPoint(-5, 5);
		p.addPoint(0, -5);
		p.addPoint(5, 5);
	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		dbg.setColor(color);

		AffineTransform t = dbg.getTransform();
		dbg.translate(X - XMundo, Y - YMundo);
		
		dbg.fill(p);
		//dbg.fillOval((int) (X - 5) - XMundo, (int) (Y - 5) - YMundo, 10, 10);
		dbg.setTransform(t);
		
		double linefx = X + 5 * Math.cos(ang);
		double linefy = Y + 5 * Math.sin(ang);
		dbg.drawLine((int) X - XMundo, (int) Y - YMundo, (int) linefx - XMundo,
				(int) linefy - YMundo);
		
		if(getTimerAnim()<100){
			dbg.setColor(Color.MAGENTA);
			dbg.drawLine((int)X- XMundo, (int)Y- YMundo, (int)getXAlvoAnim()- XMundo, (int)getYAlvoAnim()- YMundo);
		}

	}

}
