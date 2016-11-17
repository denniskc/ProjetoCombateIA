package ia;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import mainGame.Constantes;
import mainGame.Mapa;
import mainGame.Unidade;
import mainGame.UnidadeBloqueada;

public class IATesteBurro implements InterfaceIA {

	@Override
	public void rodaIaGeneral(ArrayList<Unidade> minhasUnidades,ArrayList<UnidadeBloqueada> listadeUnidades, Mapa mapa, int codExercito) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rodaIaUnidade(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes, Mapa mapa) {
		aUnidade.anda = true;
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
