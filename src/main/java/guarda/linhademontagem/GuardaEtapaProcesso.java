package guarda.linhademontagem;

public class GuardaEtapaProcesso {

	private String descricao;
	private int tempoExecucao;

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

}
