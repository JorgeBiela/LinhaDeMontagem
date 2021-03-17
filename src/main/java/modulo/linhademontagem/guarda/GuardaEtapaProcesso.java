package modulo.linhademontagem.guarda;

public class GuardaEtapaProcesso {

	private String descricao;
	private int tempoExecucao;
	private String horario;
	private boolean agendada;

	@Override
	public String toString() {
		return "Descricao: " + getDescricao() + " | Tempo: " + getTempoExecucao() + "\n";
	}

	public int getTempoExecucao() {
		return tempoExecucao;
	}

	public void setTempoExecucao(int tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAgendada() {
		return agendada;
	}

	public void setAgendada(boolean agendada) {
		this.agendada = agendada;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

}
