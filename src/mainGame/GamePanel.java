package mainGame;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

import ia.IATeste;
import ia.InterfaceIA;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class GamePanel extends Canvas implements Runnable
{
protected static final int PWIDTH = 1000;
protected static final int PHEIGHT = 600;
private Thread animator;
private boolean running = false;
private boolean gameOver = false; 

protected int FPS,SFPS;
protected int fpscount;

protected static Random rnd = new Random();

protected BufferedImage imagemcharsets;

protected boolean LEFT, RIGHT,UP,DOWN;

protected static int mousex,mousey; 

protected static ArrayList<Unidade> listadeagentes = new ArrayList<Unidade>();

protected static Mapa_Grid mapa;

protected double posx,posy;

protected static int tempo = 0;
protected static int nodosabertos = 0;

protected InterfaceIA ia1;
protected InterfaceIA ia2;

public GamePanel()
{

	setBackground(Color.white);
	setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	// create game components
	setFocusable(true);

	requestFocus(); // JPanel now receives key events	
	
	
	// Adiciona um Key Listner
	addKeyListener( new KeyAdapter() {
		public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT){
					LEFT = true;
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					RIGHT = true;
				}
				if(keyCode == KeyEvent.VK_UP){
					UP = true;
				}
				if(keyCode == KeyEvent.VK_DOWN){
					DOWN = true;
				}	
			}
		@Override
			public void keyReleased(KeyEvent e ) {
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_LEFT){
					LEFT = false;
				}
				if(keyCode == KeyEvent.VK_RIGHT){
					RIGHT = false;
				}
				if(keyCode == KeyEvent.VK_UP){
					UP = false;
				}
				if(keyCode == KeyEvent.VK_DOWN){
					DOWN = false;
				}
			}
	});
	
	addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mousex = e.getX(); 
			mousey = e.getY();
			

		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton()==3){
				int mx = (e.getX()+mapa.MapX)/16;
				int my = (e.getY()+mapa.MapY)/16;
				
				mapa.mapa[my][mx] = 1;
			}
		}
	});
	
	addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println(" "+arg0.getButton());
			
			
			if(arg0.getButton()==3){
				int mx = (arg0.getX()*2+mapa.MapX)/16;
				int my = (arg0.getY()*2+mapa.MapY)/16;
				
				mapa.mapa[my][mx] = 1;
			}
			
			if(arg0.getButton()==1){
				int mx = (arg0.getX()*2+mapa.MapX)/16;
				int my = (arg0.getY()*2+mapa.MapY)/16;
				
				synchronized (listadeagentes) {
					for(int i = 0;i < listadeagentes.size();i++){
						Unidade agente = ((Unidade)listadeagentes.get(i));
						agente.objetivox = mx;
						agente.objetivoy = my;
						agente.setouobjetivo = true;
					}
				}

			}
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	});	
	
//	for(int i = 0; i < 20; i++){
//		Color cor = Color.black;
//		
//		switch (rnd.nextInt(4)) {
//		case 0:
//			cor = Color.red;
//			break;
//		case 1:
//			cor = Color.BLUE;
//			break;
//		case 2:
//			cor = Color.green;
//			break;
//
//			
//		default:
//			break;
//		}
//		
//		
//		listadeagentes.add(new MeuAgente(10+rnd.nextInt(780), 10+rnd.nextInt(480), cor));		
//	}
	
	ia1 = new IATeste();
	ia2 = new IATeste();
	
	mousex = mousey = 0;
	
	mapa = new Mapa_Grid(200,200,125, 80);
	mapa.loadmapfromimage("/200x200.png");
	
	//time 1
	for(int i = 0; i < 100; i++){
		Color cor = new Color(255,0,0);
		int bx = 20;
		int by = 20+i; 
		Unidade agentetest = new UnidadeSoldado(bx*16,by*16,1, cor,ia1);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(128,0,0);
		int bx = 18;
		int by = 20+i; 
		Unidade agentetest = new UnidadeLanceiro(bx*16,by*16,1, cor,ia1);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(80,0,0);
		int bx = 16;
		int by = 20+i; 
		Unidade agentetest = new UnidadeArqueiro(bx*16,by*16,1, cor,ia1);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(128,60,0);
		int bx = 14;
		int by = 20+i; 
		Unidade agentetest = new UnidadeCavaleiro(bx*16,by*16,1, cor,ia1);
		listadeagentes.add(agentetest);
	}
	
	//time 2
	for(int i = 0; i < 100; i++){
		Color cor = new Color(0,0,255);
		int bx = 80;
		int by = 20+i; 
		Unidade agentetest = new UnidadeSoldado(bx*16,by*16,2, cor,ia2);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(0,0,128);
		int bx = 82;
		int by = 20+i; 
		Unidade agentetest = new UnidadeLanceiro(bx*16,by*16,2, cor,ia2);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(0,0,80);
		int bx = 84;
		int by = 20+i; 
		Unidade agentetest = new UnidadeArqueiro(bx*16,by*16,2, cor,ia2);
		listadeagentes.add(agentetest);
	}
	for(int i = 0; i < 100; i++){
		Color cor = new Color(0,60,128);
		int bx = 86;
		int by = 20+i; 
		Unidade agentetest = new UnidadeCavaleiro(bx*16,by*16,2, cor,ia2);
		listadeagentes.add(agentetest);
	}	
	
//	for(int i = 0; i < 10; i++){
//		Color cor = Color.black;
//		cor = Color.red;	
//	
//		Unidade agentetest = new Unidade(10+rnd.nextInt(1000), 10+rnd.nextInt(1000),1, cor);	
//		while(mapa.mapa[(int)(agentetest.Y/16)][(int)(agentetest.X/16)]==1){
//			agentetest.X = 10+rnd.nextInt(1000);
//			agentetest.Y = 10+rnd.nextInt(1000);
//		}
//			
//		listadeagentes.add(agentetest);
//	}
//	
//	for(int i = 0; i < 10; i++){
//		Color cor = Color.black;
//		cor = Color.BLUE;
//
//		Unidade agentetest = new Unidade(10+rnd.nextInt(1000), 10+rnd.nextInt(1000),2, cor);	
//		while(mapa.mapa[(int)(agentetest.Y/16)][(int)(agentetest.X/16)]==1){
//			agentetest.X = 10+rnd.nextInt(1000);
//			agentetest.Y = 10+rnd.nextInt(1000);
//		}
//			
//		listadeagentes.add(agentetest);
//	}
} // end of GamePanel()

public void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()

public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	int segundo = 0;
	DifTime = 0;
	TempoAnterior = System.currentTimeMillis();
	
	this.createBufferStrategy(2);
	BufferStrategy strategy = this.getBufferStrategy();
	
	while(running) {
	
		gameUpdate(DifTime); // game state is updated
		Graphics g = strategy.getDrawGraphics();
		gameRender((Graphics2D)g); // render to a buffer
		strategy.show();
	
		try {
			Thread.sleep(0); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
		
		if(segundo!=((int)(TempoAnterior/1000))){
			FPS = SFPS;
			SFPS = 1;
			segundo = ((int)(TempoAnterior/1000));
		}else{
			SFPS++;
		}
	
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()

int timerfps = 0;
private void gameUpdate(long DiffTime)
{ 
	
	if(LEFT){
		posx-=200*DiffTime/1000.0;
	}
	if(RIGHT){
		posx+=200*DiffTime/1000.0;
	}	
	if(UP){
		posy-=200*DiffTime/1000.0;
	}
	if(DOWN){
		posy+=200*DiffTime/1000.0;
	}
	
	mapa.Posiciona((int)posx,(int)posy);
	

	
	
	for(int i = 0;i < listadeagentes.size();i++){
		Agente agg = listadeagentes.get(i);
		if(agg.vivo==false){
			  listadeagentes.remove(i);
			  i--;
		}else{
			agg.SimulaSe((int)DiffTime);
		}
	}
}

private void gameRender(Graphics2D dbg)
// draw the current frame to an image buffer
{
	// clear the background
	dbg.setColor(Color.white);
	dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
	
	AffineTransform trans = dbg.getTransform();
	
	dbg.scale(0.5, 0.5);
	
	mapa.DesenhaSe(dbg);
	
	synchronized (listadeagentes) {
		for(int i = 0;i < listadeagentes.size();i++){
		  listadeagentes.get(i).DesenhaSe(dbg, mapa.MapX, mapa.MapY);
		}
	}
	

    Stroke stk = dbg.getStroke();
    dbg.setStroke(new BasicStroke(2));
    
	for(int j = 0;j < listadeagentes.size();j++){
		Unidade agente = (Unidade)listadeagentes.get(j);
		if(agente!=null&&agente.aestrela.achoufinal==false){
			dbg.setColor(Color.ORANGE);
		}else{
			dbg.setColor(agente.color);
		}
		if(agente.caminho!=null){
			for(int i = 0;i < (agente.caminho.length/2)-1;i++){
				dbg.drawLine(agente.caminho[(i*2)]*16+8-mapa.MapX, agente.caminho[(i*2)+1]*16+8 - mapa.MapY, agente.caminho[((i+1)*2)]*16+8-mapa.MapX, agente.caminho[((i+1)*2)+1]*16+8-mapa.MapY);
			}
		}
	}
    dbg.setStroke(stk);
	
	
	dbg.setTransform(trans);
	
	dbg.setColor(Color.BLUE);	
	dbg.drawString("FPS: "+FPS+"          Tempo: "+tempo+" nodosAbertos: "+nodosabertos , 10, 10);	
	
	//System.out.println("left "+LEFT);
		
}

}

