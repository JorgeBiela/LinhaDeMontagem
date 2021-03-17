package modulo.linhademontagem.guarda;

import java.util.ArrayList;
import java.util.List;

public class GuardaLinhaDeMontagem {

	private int cargaHorariaTotal;
	private int diasPossiveis;
	private List<GuardaRotinaDeTrabalho> rotinaDeTrabalho;
	private List<GuardaEtapaProcesso> totalDeEtapas;

	public int getCargaHorariaTotal() {
		return cargaHorariaTotal;
	}

	public void setCargaHorariaTotal(int cargaHorariaTotal) {
		this.cargaHorariaTotal = cargaHorariaTotal;
	}

	// public List<GuardaHorarioDeRotinaTrabalho> getHorarioRotinaTrabalho() {
	// if (rotinaDeTrabalho == null)
	// rotinaDeTrabalho = new ArrayList<>();
	//
	// return this.rotinaDeTrabalho;
	// }

	// public void setHorarioRotinaTrabalho(List<GuardaHorarioDeRotinaTrabalho>
	// horarioRotinaTrabalho) {
	// this.rotinaDeTrabalho = horarioRotinaTrabalho;
	// }

	public int getDiasPossiveis() {
		return diasPossiveis;
	}

	public void setDiasPossiveis(int diasPossiveis) {
		this.diasPossiveis = diasPossiveis;
	}

	// public List<GuardaEtapaProcesso> getListaTotalDeEtapas() {
	// return listaTotalDeEtapas;
	// }
	//
	// public void setListaTotalDeEtapas(List<GuardaEtapaProcesso>
	// listaTotalDeEtapas) {
	// this.listaTotalDeEtapas = listaTotalDeEtapas;
	// }

	public List<GuardaEtapaProcesso> getTotalDeEtapas() {
		return totalDeEtapas;
	}

	public void setTotalDeEtapas(List<GuardaEtapaProcesso> totalDeEtapas) {
		this.totalDeEtapas = totalDeEtapas;
	}

	public List<GuardaRotinaDeTrabalho> getRotinaDeTrabalho() {
		return rotinaDeTrabalho;
	}

	public void setRotinaDeTrabalho(List<GuardaRotinaDeTrabalho> rotinaDeTrabalho) {
		this.rotinaDeTrabalho = rotinaDeTrabalho;
	}

	
}
