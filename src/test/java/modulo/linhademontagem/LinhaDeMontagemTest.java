package modulo.linhademontagem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import modulo.linhademontagem.guarda.GuardaEtapaProcesso;

public class LinhaDeMontagemTest {

	@Test
	public void leituraDoArquivoDeveRetornarVazio() {
		LinhaDeMontagemControler controler = new LinhaDeMontagemControler();

		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso("");

		assertTrue(arquivo.isEmpty());
	}

	@Test
	public void leituraDoArquivoNaoDeveRetornarVazio() {
		LinhaDeMontagemControler controler = new LinhaDeMontagemControler();

		String path = "/arquivos/LinhaDeMontagem.txt";
		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso(path);

		assertTrue(arquivo.isPresent());

	}

	@Test
	public void leituraDoArquivoDeveGerarDuasLinhaDeMontagem() {
		LinhaDeMontagemControler controler = new LinhaDeMontagemControler();

		String path = "/arquivos/LinhaDeMontagem.txt";
		Optional<List<GuardaEtapaProcesso>> arquivo = controler.lerArquivoDeEtapasDeProcesso(path);

		if (arquivo.isPresent())
			assertNotNull(controler.gerarLinhasDeMontagem(arquivo.get()));
		else
			fail();

	}

	// ARQUIVO VAZIO

	// ATRIBUICAO DE TEMPO CASO HAVER 'maintenance'

	// CARACTER INVALIDO

	// DESCRICAO OU TEMPO NAO PREENCHIDO

	// CARGA HORARIO VAZIA

	// TESTE DE SOBRECARGA

	//
}
