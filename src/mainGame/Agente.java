package mainGame;
import java.awt.Graphics2D;


public abstract class Agente {
	
	protected double X,Y;
	boolean vivo = true;
	
	public abstract void SimulaSe(int DiffTime);
	public abstract void DesenhaSe(Graphics2D dbg,int XMundo,int YMundo);
	
}
