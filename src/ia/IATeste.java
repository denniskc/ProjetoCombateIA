package ia;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import mainGame.Constantes;
import mainGame.Mapa;
import mainGame.Unidade;
import mainGame.UnidadeBloqueada;

public class IATeste implements InterfaceIA {

	@Override
	public void rodaIaGeneral(ArrayList<Unidade> minhasUnidades,ArrayList<UnidadeBloqueada> listadeUnidades, Mapa mapa, int codExercito) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rodaIaUnidade(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes, Mapa mapa) {
		
		if(aUnidade.inimigoAlvo!=null){
			float dist = aUnidade.getDist2(aUnidade.inimigoAlvo);
			if(aUnidade.getRaioAtaque()*aUnidade.getRaioAtaque()>dist){
				aUnidade.ataca(aUnidade.inimigoAlvo);
			}
		}
		
		if(aUnidade.inimigoAlvo!=null){
			UnidadeBloqueada inimigo = aUnidade.inimigoAlvo;
			
			if(inimigo.getVivo()==false){
				aUnidade.caminho  = null;
				aUnidade.estado = 0;
				aUnidade.inimigoAlvo = null;
			}else{
				double dx = (inimigo.getX()/16)-aUnidade.objetivox;
				double dy = (inimigo.getY()/16)-aUnidade.objetivoy;
				
				if((dx*dx+dy*dy)>25){
					aUnidade.caminho  = null;
				}
			}
		}

		
		if(aUnidade.estado ==0 || aUnidade.caminho == null){
			UnidadeBloqueada ui = unidadeMaisProxima(aUnidade,listadeAgentes);
			if(ui!=null){
				//System.out.println("MeuTime "+aUnidade.getTime()+" objetivo "+ui.getTime());
				aUnidade.objetivox = (int)ui.getX()/16;
				aUnidade.objetivoy = (int)ui.getY()/16;
				aUnidade.setaObjetivo(aUnidade.objetivox,aUnidade.objetivoy);
				aUnidade.estado = 1;
				aUnidade.inimigoAlvo = ui;
			}else{
				aUnidade.anda = false;
			}
		}else{
			if (aUnidade.caminho != null && (aUnidade.estado * 2) < aUnidade.caminho.length) {
				double dx = ((aUnidade.caminho[aUnidade.estado * 2] * 16) + 8) - aUnidade.getX();
				double dy = ((aUnidade.caminho[aUnidade.estado * 2 + 1] * 16) + 8) - aUnidade.getY();
	
				double dist = dx * dx + dy * dy;
	
				if (dist < 25) {
					aUnidade.estado++;
				} else {
					aUnidade.ang = Math.atan2(dy, dx);
				}
				aUnidade.anda = true;
			} else {
				aUnidade.anda = false;
				aUnidade.estado = 0;
			}
		}
	}
	
	UnidadeBloqueada unidadeMaisProxima(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes){
		double dist = Double.MAX_VALUE;
		UnidadeBloqueada maisproxima = null;
		for(int i = 0; i < listadeAgentes.size();i++){
			UnidadeBloqueada un = listadeAgentes.get(i);
			if(un.getCodigoUnico()!=aUnidade.getCodigoUnico()&&un.getTime()!=aUnidade.getTime()){
				double dist2 = un.getDist2(aUnidade);
				if(dist2<dist){
					dist = dist2;
					maisproxima = un;
				}
			}
		}
		
		return maisproxima;
	}

}
