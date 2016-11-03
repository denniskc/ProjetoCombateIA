package ia;

import java.util.ArrayList;

import mainGame.Mapa;
import mainGame.Unidade;
import mainGame.UnidadeBloqueada;

public interface InterfaceIA {
	public void rodaIaGeneral(ArrayList<Unidade> minhasUnidades,ArrayList<UnidadeBloqueada> listadeUnidades,Mapa mapa,int codExercito);
	public void rodaIaUnidade(Unidade aUnidade,ArrayList<UnidadeBloqueada> listadeAgentes,Mapa mapa);
}
