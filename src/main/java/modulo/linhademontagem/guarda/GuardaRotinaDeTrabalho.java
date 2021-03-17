package modulo.linhademontagem.guarda;

import java.util.ArrayList;
import java.util.List;

public class GuardaRotinaDeTrabalho {
	private List<GuardaHorarioDeRotinaTrabalho> periodoDeTrabalho;

	public List<GuardaHorarioDeRotinaTrabalho> getPeriodoDeTrabalho() {
		if (periodoDeTrabalho == null)
			periodoDeTrabalho = new ArrayList<>();
		return periodoDeTrabalho;
	}

	public void setPeriodoDeTrabalho(List<GuardaHorarioDeRotinaTrabalho> periodoDeTrabalho) {
		this.periodoDeTrabalho = periodoDeTrabalho;
	}
}