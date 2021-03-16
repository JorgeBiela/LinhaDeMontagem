package modulo.linhademontagem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import guarda.linhademontagem.GuardaEtapaProcesso;
import guarda.linhademontagem.GuardaHorarioDeRotinaTrabalho;
import guarda.linhademontagem.GuardaLinhaDeMontagem;
import sistema.Dialogo;

public class ControlerLinhaDeMontagem {

	private GuardaLinhaDeMontagem linhaDeMontagem = new GuardaLinhaDeMontagem();

	public Optional<List<GuardaEtapaProcesso>> lerArquivoDeEtapasDeProcesso(String caminhoArquivo) {

		List<GuardaEtapaProcesso> listaProcessos = new ArrayList<>();

		try {

			File file = new File(getClass().getResource(caminhoArquivo).toURI());

			FileInputStream ifile = new FileInputStream(file);

			DataInputStream input = new DataInputStream(ifile);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
			String stringLines = buffer.readLine();

			while (stringLines != null) {
				stringLines = buffer.readLine();

				if (stringLines != null) {
					Optional<GuardaEtapaProcesso> gEtapa = trataLinhaArquivo(stringLines);

					if (gEtapa.isPresent())
						listaProcessos.add(gEtapa.get());
				}

			}

			input.close();
		} catch (Exception e) {
			Dialogo.showMenssageErro("ERRO NA LEITURA DO ARQUIVO\n  Tente novamente. ");
			e.getStackTrace();
			return Optional.empty();
		}

		return Optional.of(listaProcessos);

	}

	private Optional<GuardaEtapaProcesso> trataLinhaArquivo(String linhaArquivo) {

		GuardaEtapaProcesso gEtapaProcesso = new GuardaEtapaProcesso();

		int iniDefinicaoTempo = linhaArquivo.lastIndexOf(" ");

		if (iniDefinicaoTempo < 0) {
			Dialogo.showMenssageErro("FORMATACAO DO TEXTO\n  Etapa de processo com tempo não definido.");
			return Optional.empty();
		}

		String descricao = linhaArquivo.substring(0, iniDefinicaoTempo);
		String time = linhaArquivo.substring(iniDefinicaoTempo + 1);

		if (descricao == null || descricao.trim().equals("")) {
			Dialogo.showMenssageErro("FORMATACAO DO TEXTO\n  Etapa de processo sem descrição.");
			return Optional.empty();
		} else
			gEtapaProcesso.setDescricao(descricao);

		try {
			time = time.toUpperCase();

			if (time.contains("MIN")) {
				int tempo = Integer.parseInt(time.replace("MIN", "").trim());
				gEtapaProcesso.setTempoExecucao(tempo);

			} else if (time.contains("MAINTENANCE") || (time.contains("MANUTEN"))) {
				gEtapaProcesso.setTempoExecucao(5);
			}

		} catch (Exception e) {
			Dialogo.showMenssageErro("FORMATACAO DO TEXTO\n  "
					+ "Etapa de processo com tempo definido mal formatado. \n"
					+ linhaArquivo);
			return Optional.empty();
		}

		return Optional.of(gEtapaProcesso);

	}

	public Optional<List<GuardaLinhaDeMontagem>> gerarLinhasDeMontagem(List<GuardaEtapaProcesso> arquivo) {

		List<GuardaLinhaDeMontagem> listaDeMontagem = new ArrayList<>();

		return Optional.ofNullable(listaDeMontagem);
	}

	public void configurarHorarioDeTrabalhoPadrao() {

		if (linhaDeMontagem.getHorarioRotinaTrabalho().isEmpty()) {
			List<GuardaHorarioDeRotinaTrabalho> listaDeHorarios = new ArrayList<>();

			GuardaHorarioDeRotinaTrabalho gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
			gHorarioRotina.setInicio(new Time(9 * 3600));
			gHorarioRotina.setFim(new Time(12 * 3600));
			gHorarioRotina.setDescricao("Período da manhã");
			listaDeHorarios.add(gHorarioRotina);

			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
			gHorarioRotina.setInicio(new Time(12 * 3600));
			gHorarioRotina.setFim(new Time(13 * 3600));
			gHorarioRotina.setDescricao("Período do Almoço");
			gHorarioRotina.setDescanco(true);

			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
			gHorarioRotina.setInicio(new Time(13 * 3600));
			gHorarioRotina.setFim(new Time(17 * 3600));
			gHorarioRotina.setDescricao("Período da Tarde");

			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
			gHorarioRotina.setInicio(new Time(16 * 3600));
			gHorarioRotina.setFim(new Time(1 * 3600));
			gHorarioRotina.setDescricao("Atividades de Ginástica");
			gHorarioRotina.setDescanco(true);

			listaDeHorarios.add(gHorarioRotina);

			linhaDeMontagem.setHorarioRotinaTrabalho(listaDeHorarios);
		}

	}

}
