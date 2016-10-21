package aestrela;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import mainGame.Mapa;


public class AEstrela {
	Mapa mapa;
		
	public LinkedList<nodo> nodosAbertos = new LinkedList<nodo>();
	public LinkedList<nodo> nodosFechados = new LinkedList<nodo>();
	HashSet<Integer> fechadosSet = new HashSet<Integer>();
	
	int objetivox = 0;
	int objetivoy = 0;
	
	public boolean achoufinal = true;
	public boolean iniciouAestrela = false;
	nodo selecionado = null;
	
	public AEstrela(Mapa _mapa) {
		// TODO Auto-generated constructor stub
		mapa = _mapa;
	}
	
	public int[] StartAestrela(int x,int y,int objx,int objy,int nestados){
		objetivox = objx;
		objetivoy = objy;
		
		synchronized (nodosAbertos) {
			nodosAbertos.clear();			
		}
		synchronized(nodosFechados){
			nodosFechados.clear();
		}
		
		fechadosSet.clear();
		
		selecionado = new nodo(null, x, y, 0);
		
		boolean nodofinal = false;
		
		for(int i = 0; i < nestados;i++){
			if(abreNodo(selecionado)){
				selecionado = nodosFechados.get(nodosFechados.size()-1);
				nodofinal = true;
				break;
			}
			double menor = 99999999;
			double menoheuristica = 99999999;
			nodo menoridx = null;
			
			for (Iterator iterator = nodosAbertos.iterator(); iterator.hasNext();) {
				nodo nodo2 = (nodo) iterator.next();
				double soma = nodo2.energia+nodo2.euristica;
				
				//System.out.println(" z "+z+" "+soma);
				
				if(soma<menor){
					menor = soma;
					menoridx = nodo2;
					menoheuristica = nodo2.euristica;
				}else if(soma==menor){
					if(nodo2.euristica<menoheuristica){
						menor = soma;
						menoridx = nodo2;
						menoheuristica = nodo2.euristica;
					}
				}
			}

			//System.out.println("menoridx "+menoridx+" test "+nodosAbertos.size());
			
			if(menoridx==null){
				return null;
			}
			
			selecionado = menoridx;
			nodosAbertos.remove(menoridx);
		}
		
		ArrayList<nodo> caminho = new ArrayList<nodo>();
		
		nodo onodo = selecionado;
		
		caminho.add(onodo);
		
		while(onodo.pai!=null){
			onodo = onodo.pai;
			caminho.add(0,onodo);
		}
		
		int ocaminho[] = new int[caminho.size()*2];
		for(int i =  0; i < caminho.size();i++){
			nodo nd = caminho.get(i);
			ocaminho[i*2] = nd.x;
			ocaminho[(i*2)+1] = nd.y;
		}
		
		
		if(nodofinal==false){
			achoufinal = false;
			iniciouAestrela = true;
		}
		
		
		
		return ocaminho;
	}
	
	public int[] continuapath(int nodosTest){
		
		for(int i = 0; i < nodosTest;i++){
			if(abreNodo(selecionado)){
				selecionado = nodosFechados.get(nodosFechados.size()-1);
				achoufinal = true;
				break;
			}
			double menor = 99999999;
			double menoheuristica = 99999999;
			nodo menoridx = null;
			
			for (Iterator iterator = nodosAbertos.iterator(); iterator.hasNext();) {
				nodo nodo2 = (nodo) iterator.next();
				double soma = nodo2.energia+nodo2.euristica;
				
				//System.out.println(" z "+z+" "+soma);
				
				if(soma<menor){
					menor = soma;
					menoridx = nodo2;
					menoheuristica = nodo2.euristica;
				}else if(soma==menor){
					if(nodo2.euristica<menoheuristica){
						menor = soma;
						menoridx = nodo2;
						menoheuristica = nodo2.euristica;
					}
				}
			}

			//System.out.println("menoridx "+menoridx+" test "+nodosAbertos.size());
			
			if(menoridx==null){
				return null;
			}
			
			selecionado = menoridx;
			nodosAbertos.remove(menoridx);
		}
		
		if(achoufinal){
		
			ArrayList<nodo> caminho = new ArrayList<nodo>();
			
			nodo onodo = nodosFechados.get(nodosFechados.size()-1);
			
			caminho.add(onodo);
			
			while(onodo.pai!=null){
				onodo = onodo.pai;
				caminho.add(0,onodo);
			}
			
			int ocaminho[] = new int[caminho.size()*2];
			for(int i =  0; i < caminho.size();i++){
				nodo nd = caminho.get(i);
				ocaminho[i*2] = nd.x;
				ocaminho[(i*2)+1] = nd.y;
			}
			
			iniciouAestrela = false;
			
			return ocaminho;
		}else{
			return null;
		}
	}
	
	public boolean abreNodo(nodo onodo){
		nodosFechados.add(onodo);
		fechadosSet.add(onodo.x+onodo.y*100);
		
		nodo candidatos[] = new nodo[8];
		
		candidatos[0] = new nodo(onodo, onodo.x+1, onodo.y, onodo.energia+1);
		candidatos[1] = new nodo(onodo, onodo.x, onodo.y+1, onodo.energia+1);
		candidatos[2] = new nodo(onodo, onodo.x-1, onodo.y, onodo.energia+1);
		candidatos[3] = new nodo(onodo, onodo.x, onodo.y-1, onodo.energia+1);
		
		candidatos[4] = new nodo(onodo, onodo.x+1, onodo.y+1, onodo.energia+1.4);
		candidatos[5] = new nodo(onodo, onodo.x-1, onodo.y+1, onodo.energia+1.4);
		candidatos[6] = new nodo(onodo, onodo.x-1, onodo.y-1, onodo.energia+1.4);
		candidatos[7] = new nodo(onodo, onodo.x+1, onodo.y-1, onodo.energia+1.4);
		
		for(int i = 0; i < 8; i++){
			nodo ntest = candidatos[i];
			
			if(ntest.x==objetivox&&ntest.y==objetivoy){
			    nodosFechados.add(ntest);
				return true;
			}
			
			if(ntest.x<0||ntest.y<0||ntest.x>=mapa.getLargura()||ntest.y>=mapa.getAltura()){
				continue;
			}
			if(mapa.getMapPos(ntest.x,ntest.y)==1){
				continue;
			}
			
			boolean igual = false;
			
			synchronized (nodosFechados) {	
				igual = testaNodosFechados(ntest);
			}
			if(igual){
				continue;
			}
			
			igual = testaNodosAbertos(ntest);
			if(igual){
				continue;
			}			
			
			ntest.euristica = calculaheuristica(ntest.x, ntest.y, objetivox, objetivoy);
			nodosAbertos.add(ntest);
		}
		
		return false;
	}

	private boolean testaNodosAbertos(nodo ntest) {
		boolean igual = false;
		for (Iterator iterator = nodosAbertos.iterator(); iterator.hasNext();) {
			nodo nodo2 = (nodo) iterator.next();
			if(ntest.x == nodo2.x&&ntest.y==nodo2.y){
				igual=true;
				break;
			}
		}
		return igual;
	}

	private boolean testaNodosFechados(nodo ntest) {
		boolean igual = false;
		int val = ntest.x+ntest.y*100;
//		for (Iterator iterator = nodosFechados.iterator(); iterator.hasNext();) {
//			nodo nodo2 = (nodo) iterator.next();
//			if(nodo2!=null){
//				if(ntest.x == nodo2.x&&ntest.y==nodo2.y){
//					igual=true;
//					break;
//				}
//			}
//		}
		
		if(fechadosSet.contains(val)){
			return true;
		}
		return igual;
	}

	public double calculaheuristica(int x,int y,int objx,int objy){
		double difx =  objx-x;
		double dify =  objy-y;
		return Math.sqrt(difx*difx+dify*dify);
	}
}

class nodo{
	nodo pai;
	int x;
	int y;
	double energia;
	double euristica;
	
	public nodo(nodo pai,int x,int y, double energia) {
		// TODO Auto-generated constructor stub
		this.pai = pai;
		this.x = x;
		this.y = y;
		this.energia = energia;
	}
}