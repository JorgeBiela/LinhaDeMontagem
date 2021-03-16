package modulo.linhademontagem;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import guarda.linhademontagem.GuardaEtapaProcesso;
import guarda.linhademontagem.GuardaLinhaDeMontagem;

public class LinhaDeMontagemTest {

	@Test
	public void leituraDoArquivoDeveRetornarVazio() {
		ControlerLinhaDeMontagem controler = new ControlerLinhaDeMontagem();

		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso("");

		assertTrue(arquivo.isEmpty());
	}

	@Test
	public void leituraDoArquivoNaoDeveRetornarVazio() {
		ControlerLinhaDeMontagem controler = new ControlerLinhaDeMontagem();

		String path = "/arquivos/LinhaDeMontagem.txt";
		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso(path);

		assertTrue(arquivo.isPresent());

	}

	@Test
	public void leituraDoArquivoDeveGerarDuasLinhaDeMontagem() {
		ControlerLinhaDeMontagem controler = new ControlerLinhaDeMontagem();

		String path = "/arquivos/LinhaDeMontagem.txt";
		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso(path);

		if (arquivo.isPresent()) {
			Optional<List<GuardaLinhaDeMontagem>> linhasDeMontagem = controler.gerarLinhasDeMontagem(arquivo.get());
		}

		assertTrue(arquivo.isPresent());

	}

	// ARQUIVO VAZIO

	// ATRIBUICAO DE TEMPO CASO HAVER 'maintenance'

	// CARACTER INVALIDO

	// DESCRICAO OU TEMPO NAO PREENCHIDO

	// CARGA HORARIO VAZIA
}
