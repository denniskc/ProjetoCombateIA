package ia;

import java.util.ArrayList;

import mainGame.Mapa;
import mainGame.Unidade;

public class IATeste implements InterfaceIA {

	@Override
	public void rodaIaGeneral(ArrayList<Unidade> listadeUnidades, Mapa mapa, int codExercito) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rodaIaUnidade(Unidade aUnidade, ArrayList<Unidade> listadeAgentes, Mapa mapa) {
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
		}
	}

}
