package ia;

import java.util.ArrayList;

import mainGame.Mapa;
import mainGame.Unidade;

public interface InterfaceIA {
	public void rodaIaGeneral(ArrayList<Unidade> listadeUnidades,Mapa mapa,int codExercito);
	public void rodaIaUnidade(Unidade aUnidade,ArrayList<Unidade> listadeAgentes,Mapa mapa);
}
