package ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import mainGame.Constantes;
import mainGame.Mapa;
import mainGame.Unidade;
import mainGame.UnidadeBloqueada;

public class IADennis implements InterfaceIA {

	@Override
	public void rodaIaGeneral(ArrayList<Unidade> minhasUnidades, ArrayList<UnidadeBloqueada> listadeUnidades, Mapa mapa,
			int codExercito) {
		// TODO Auto-generated method stub
		for (int i = 0; i < minhasUnidades.size(); i++) {
			Unidade u = minhasUnidades.get(i);
			if (u.inimigoAlvo == null) {
				ArrayList<UnidadeDist> resultlist = unidadesMaisProximas(u, listadeUnidades);
				//System.out.println("RESULTLIST "+resultlist.size());
				for (int j = 0; j < 50; j++) {
					if (resultlist.size() > j) {
						UnidadeDist ud = resultlist.get(j);
						//System.out.println("" + j + " UnitDist " + ud.dist+" "+u.getTipo()+" "+ud.un.getTipo());

						if (u.getTipo() == Unidade.TIPO_SOLDADO) {
							if (ud.un.getTipo() == Unidade.TIPO_LANCEIRO) {
								u.inimigoAlvo = ud.un;
								break;
							}
						}
						if (u.getTipo() == Unidade.TIPO_LANCEIRO) {
							if (ud.un.getTipo() == Unidade.TIPO_CAVALEIRO) {
								u.inimigoAlvo = ud.un;
								break;
							}
						}
						if (u.getTipo() == Unidade.TIPO_ARQUEIRO) {
							if (ud.un.getTipo() == Unidade.TIPO_SOLDADO) {
								u.inimigoAlvo = ud.un;
								break;
							}
						}
						if (u.getTipo() == Unidade.TIPO_CAVALEIRO) {
							if (ud.un.getTipo() == Unidade.TIPO_ARQUEIRO) {
								u.inimigoAlvo = ud.un;
								break;
							}
						}

					} else {
						break;
					}
				}
				if(u.inimigoAlvo==null){
					if(resultlist.size()>0){
						UnidadeDist ud = resultlist.get(0);
						u.inimigoAlvo = ud.un;
					}
				}
				u.caminho = null;
			}
		}
	}

	@Override
	public void rodaIaUnidade(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes, Mapa mapa) {
		if(aUnidade.caminho==null){
			if(aUnidade.inimigoAlvo!=null){
				aUnidade.objetivox = (int)aUnidade.inimigoAlvo.getX()/16;
				aUnidade.objetivoy = (int)aUnidade.inimigoAlvo.getY()/16;
				aUnidade.setaObjetivo(aUnidade.objetivox,aUnidade.objetivoy);
				aUnidade.estado = 1;
				//System.out.println("Seta OBJETIVO");
			}else{
				aUnidade.anda = false;
			}
		}
		
		if(aUnidade.inimigoAlvo!=null){
			float dist = aUnidade.getDist2(aUnidade.inimigoAlvo);
			if(aUnidade.getRaioAtaque()*aUnidade.getRaioAtaque()>dist){
				aUnidade.ataca(aUnidade.inimigoAlvo);
			}else{
				UnidadeBloqueada ui = unidadeMaisProxima(aUnidade,listadeAgentes);
				dist = aUnidade.getDist2(ui);
				if(aUnidade.getRaioAtaque()*aUnidade.getRaioAtaque()>dist){
					aUnidade.ataca(ui);
				}
			}
		}
		
		if(aUnidade.inimigoAlvo!=null){
			UnidadeBloqueada inimigo = aUnidade.inimigoAlvo;
			
			if(inimigo.getVivo()==false){
				aUnidade.caminho  = null;
				aUnidade.estado = 0;
				aUnidade.inimigoAlvo = null;
				aUnidade.anda = false;
			}else{
				double dx = (inimigo.getX()/16)-aUnidade.objetivox;
				double dy = (inimigo.getY()/16)-aUnidade.objetivoy;
				
				if((dx*dx+dy*dy)>25){
					aUnidade.caminho  = null;
				}
			}
		}
		
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
		}		
		
	}

	UnidadeBloqueada unidadeMaisProxima(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes) {
		double dist = Double.MAX_VALUE;
		UnidadeBloqueada maisproxima = null;
		for (int i = 0; i < listadeAgentes.size(); i++) {
			UnidadeBloqueada un = listadeAgentes.get(i);
			if (un.getCodigoUnico() != aUnidade.getCodigoUnico() && un.getTime() != aUnidade.getTime()) {
				double dist2 = un.getDist2(aUnidade);
				if (dist2 < dist) {
					dist = dist2;
					maisproxima = un;
				}
			}
		}

		return maisproxima;
	}

	ArrayList<UnidadeDist> unidadesMaisProximas(Unidade aUnidade, ArrayList<UnidadeBloqueada> listadeAgentes) {
		ArrayList<UnidadeDist> resultlist = new ArrayList<>();

		for (int i = 0; i < listadeAgentes.size(); i++) {
			UnidadeBloqueada un = listadeAgentes.get(i);
			if (un.getCodigoUnico() != aUnidade.getCodigoUnico() && un.getTime() != aUnidade.getTime()) {
				double dist2 = un.getDist2(aUnidade);
				UnidadeDist ud = new UnidadeDist(un, dist2);
				resultlist.add(ud);
			}
		}

		Collections.sort(resultlist, new Comparator<UnidadeDist>() {
			@Override
			public int compare(UnidadeDist o1, UnidadeDist o2) {

				return o1.dist < o2.dist ? -1 : o1.dist > o2.dist ? 1 : 0;
			}
		});

		return resultlist;
	}

}

class UnidadeDist {
	UnidadeBloqueada un;
	double dist;

	public UnidadeDist(UnidadeBloqueada un, double dist) {
		this.un = un;
		this.dist = dist;
	}
}
