package guarda.linhademontagem;

import java.util.List;
import java.util.Optional;

public class GuardaLinhaDeMontagem {

	private int codLinhaMontagem;
	private int cargaHorariaTotal;
	private List<GuardaHorarioDeRotinaTrabalho> horarioRotinaTrabalho;

	public int getCodLinhaMontagem() {
		return codLinhaMontagem;
	}

	public void setCodLinhaMontagem(int codLinhaMontagem) {
		this.codLinhaMontagem = codLinhaMontagem;
	}

	public int getCargaHorariaTotal() {
		return cargaHorariaTotal;
	}

	public void setCargaHorariaTotal(int cargaHorariaTotal) {
		this.cargaHorariaTotal = cargaHorariaTotal;
	}

	public Optional<List<GuardaHorarioDeRotinaTrabalho>> getHorarioRotinaTrabalho() {
		return Optional.ofNullable(horarioRotinaTrabalho);
	}

	public void setHorarioRotinaTrabalho(List<GuardaHorarioDeRotinaTrabalho> horarioRotinaTrabalho) {
		this.horarioRotinaTrabalho = horarioRotinaTrabalho;
	}

}
