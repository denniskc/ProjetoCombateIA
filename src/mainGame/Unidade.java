package mainGame;
import java.awt.Color;
import java.awt.Graphics2D;

import aestrela.AEstrela;
import ia.InterfaceIA;

public class Unidade extends Agente {
	
	public int TIPO_SOLDADO = 1;
	public int TIPO_LANCEIRO = 2;
	public int TIPO_ARQUEIRO = 3;
	public int TIPO_CAVALEIRO = 4;

	protected Color color;
	protected double oldx = 0;
	protected double oldy = 0;
	protected int timeria = 0;
	protected boolean colidiu = false;
	
	protected InterfaceIA ia = null;
	
	//Informacao
	protected int oTime = 0;
	protected int tipo = 0;
	protected float life = 0;
	protected float dano = 0;
	protected float vel = 40;
	protected float raioAtaque = 0;

	protected int timerAtaque = 0;

	public AEstrela aestrela;
	public double ang = 0;
	public int estado = 0;
	public int caminho[] = null;
	public boolean setouobjetivo = false;
	public int objetivox = 0;
	public int objetivoy = 0;
	public boolean anda = true;
	public Unidade inimigoAlvo = null; 
	
	private double xalvoanim = 0;
	private double yalvoanim = 0;
	private int timeranim = 1000;

	public Unidade(int x, int y, int oTime,Color color, InterfaceIA ia) {
		// TODO Auto-generated constructor stub
		X = x;
		Y = y;

		aestrela = new AEstrela(GamePanel.mapa);

		this.color = color;
		this.oTime = oTime;
		
		this.ia = ia;
	}

	@Override
	public void SimulaSe(int DiffTime) {
		// TODO Auto-generated method stub
		timeria += DiffTime;
		timerAtaque += DiffTime;
		timeranim += DiffTime;

		if (setouobjetivo == true) {
			setaObjetivo(objetivox, objetivoy);
			setouobjetivo = false;
		}

		if (aestrela.iniciouAestrela) {
			if (aestrela.achoufinal == false) {
				int[] retorno = aestrela.continuapath(20);
				if (retorno != null) {
					AEstrela atmp = new AEstrela(GamePanel.mapa);
					int[] caminho2 = atmp.StartAestrela((int) (X / 16),
							(int) (Y / 16), retorno[0], retorno[1], 1000);
					int[] ctmp = retorno;

					if (caminho2 != null) {

						for (int i = 0; i < (caminho2.length / 2); i++) {
							int pat1x = caminho2[i * 2];
							int pat1y = caminho2[i * 2 + 1];
							for (int j = 0; j < (retorno.length / 2); j++) {
								if (pat1x == retorno[j * 2]
										&& pat1y == retorno[j * 2 + 1]) {
									caminho = new int[i * 2
											+ ((retorno.length / 2) - j) * 2];
									for (int z = 0; z < i * 2; z++) {
										caminho[z] = caminho2[z];
									}
									for (int z = 0; z < ((retorno.length / 2) - j) * 2; z++) {
										caminho[i * 2 + z] = retorno[j * 2 + z];
									}
									System.out.println(" caminho "
											+ caminho.length);
									aestrela.achoufinal = true;
									estado = 0;
									return;
								}
							}
						}
					}
				}

			}
		}
		oldx = X;
		oldy = Y;

		if (timeria > 10) {
			calculaIA(DiffTime);
			timeria = 0;
		}

		if(anda){
			X += Math.cos(ang) * vel * DiffTime / 1000.0;
			Y += Math.sin(ang) * vel * DiffTime / 1000.0;
		}

		if (X < 0) {
			X = oldx;
		}
		if (Y < 0) {
			Y = oldy;
		}
		if (X >= GamePanel.mapa.getLargura() * 16) {
			X = oldx;
		}
		if (Y >= GamePanel.mapa.getAltura() * 16) {
			Y = oldy;
		}

		int bx = (int) (X / 16);
		int by = (int) (Y / 16);
		int bxold = (int) (oldx / 16);
		int byold = (int) (oldy / 16);

		if (GamePanel.mapa.mapa[by][bx] == 1) {

			if (GamePanel.mapa.mapa[byold][bx] == 0) {
				Y = oldy;
			} else if (GamePanel.mapa.mapa[by][bxold] == 0) {
				X = oldx;
			} else {
				Y = oldy;
				X = oldx;
			}
		}

		for (int i = 0; i < GamePanel.listadeagentes.size(); i++) {
			Agente agente = GamePanel.listadeagentes.get(i);

			if (agente != this) {

				double dax = agente.X - X;
				double day = agente.Y - Y;

				double dista = dax * dax + day * day;

				if (dista < 100) {
					X = oldx;
					Y = oldy;

					colidiu = true;

					break;
				}
			}
		}

	}

	@Override
	public void DesenhaSe(Graphics2D dbg, int XMundo, int YMundo) {
		// TODO Auto-generated method stub
		dbg.setColor(color);

		dbg.fillOval((int) (X - 5) - XMundo, (int) (Y - 5) - YMundo, 10, 10);

		double linefx = X + 5 * Math.cos(ang);
		double linefy = Y + 5 * Math.sin(ang);
		dbg.drawLine((int) X - XMundo, (int) Y - YMundo, (int) linefx - XMundo,
				(int) linefy - YMundo);
		
		if(timeranim<100){
			dbg.setColor(Color.MAGENTA);
			dbg.drawLine((int)X- XMundo, (int)Y- YMundo, (int)xalvoanim- XMundo, (int)yalvoanim- YMundo);
		}

	}

	public void setaObjetivo(int objetivox, int objetivoy) {
		long tempoinicio = System.currentTimeMillis();
		caminho = aestrela.StartAestrela((int) (X / 16), (int) (Y / 16),objetivox, objetivoy, 100);
		GamePanel.tempo = (int) (System.currentTimeMillis() - tempoinicio);
		GamePanel.nodosabertos = aestrela.nodosAbertos.size()+ aestrela.nodosFechados.size();
		estado = 0;
	}

	public void calculaIA(int DiffTime) {
		ia.rodaIaUnidade(this,GamePanel.listadeagentes, GamePanel.mapa);
	}
	
	public int getTime(){return oTime;}
	public int getTipo(){return tipo;}
	public float getLife(){return life;}
	public float getDano(){return dano;}
	public float getVel(){return vel;}
	public float getRaioAtaque(){return raioAtaque;}
	public double getX(){return X;}
	public double getY(){return Y;}
	
	public float getDist2(Unidade un){
		double dx = X-un.X;
		double dy = Y-un.Y;
		return (float)(dx*dx+dy*dy);
	}
	public double getAng(Unidade un){
		double dx = un.X-X;
		double dy = un.Y-Y;
		return Math.atan2(dx, dy);
	}
	
	protected void levaDano(float dano){
		life-=dano;
		if(life<=0){
			vivo = false;
		}
	}
	
	public void ataca(Unidade un){
		if(timerAtaque>250){
			float dist = getDist2(un);
			if(dist < raioAtaque*raioAtaque){
				timerAtaque = 0;
				if(Constantes.rnd.nextInt(10)>2){
					float dano2 = dano/2+(Constantes.rnd.nextFloat()*(dano));
					un.levaDano(dano);
					xalvoanim = un.getX();
					yalvoanim = un.getY();
					timeranim = 0;
				}
			}
		}
	}
	
	public boolean getVivo(){
		return vivo;
	}
}
