package guarda.linhademontagem;

import java.sql.Time;

public class GuardaHorarioDeRotinaTrabalho {
	private Time inicio;
	private Time fim;

	private String descricao;

	private boolean isDescanco;

	public Time getInicio() {
		return inicio;
	}

	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}

	public Time getFim() {
		return fim;
	}

	public void setFim(Time fim) {
		this.fim = fim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isDescanco() {
		return isDescanco;
	}

	public void setDescanco(boolean isDescanco) {
		this.isDescanco = isDescanco;
	}
}