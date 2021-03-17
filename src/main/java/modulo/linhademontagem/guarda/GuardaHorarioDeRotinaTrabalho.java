package modulo.linhademontagem.guarda;

import java.util.List;

public class GuardaHorarioDeRotinaTrabalho {
	private String horario;
	private String descricao;
	private EnumPeriodo periodo;
	private List<List<GuardaEtapaProcesso>> etapas;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EnumPeriodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(EnumPeriodo periodo) {
		this.periodo = periodo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public List<List<GuardaEtapaProcesso>> getEtapas() {
		return etapas;
	}

	public void setEtapas(List<List<GuardaEtapaProcesso>> etapas) {
		this.etapas = etapas;
	}
}