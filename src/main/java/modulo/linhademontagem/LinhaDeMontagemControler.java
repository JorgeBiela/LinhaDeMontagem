package modulo.linhademontagem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import modulo.linhademontagem.guarda.GuardaEtapaProcesso;
import modulo.linhademontagem.guarda.GuardaHorarioDeRotinaTrabalho;
import modulo.linhademontagem.guarda.GuardaLinhaDeMontagem;
import modulo.sistema.Dialogo;

public class LinhaDeMontagemControler {

	private GuardaLinhaDeMontagem linhaDeMontagem = new GuardaLinhaDeMontagem();
	List<List<GuardaEtapaProcesso>> datedConversationsList = new ArrayList<List<GuardaEtapaProcesso>>();

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
			Dialogo.showMenssageErro("FORMATACAO DO TEXTO\n  Etapa de processo com tempo n?o definido.");
			return Optional.empty();
		}

		String descricao = linhaArquivo.substring(0, iniDefinicaoTempo);
		String time = linhaArquivo.substring(iniDefinicaoTempo + 1);

		if (descricao == null || descricao.trim().equals("")) {
			Dialogo.showMenssageErro("FORMATACAO DO TEXTO\n  Etapa de processo sem descri??o.");
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
					+ "Etapa de processo com tempo definido mal formatado. \n" + linhaArquivo);
			return Optional.empty();
		}

		return Optional.of(gEtapaProcesso);

	}

	private int getTempoTotalDasEtapas(List<GuardaEtapaProcesso> listaDeEtapas) {
		if (listaDeEtapas == null || listaDeEtapas.isEmpty())
			return 0;

		int total = 0;
		for (GuardaEtapaProcesso Conversation : listaDeEtapas)
			total += Conversation.getTempoExecucao();

		return total;
	}

	private List<List<GuardaEtapaProcesso>> getAgendamento(List<GuardaEtapaProcesso> listaDeEtapasOriginal) {

		int tempoMinimoDoDia = 6 * 60;
		int tempoTotal = getTempoTotalDasEtapas(listaDeEtapasOriginal);
		int diasPossiveis = tempoTotal / tempoMinimoDoDia;

		List<GuardaEtapaProcesso> listaTemporariaDeEtapas = new ArrayList<GuardaEtapaProcesso>();
		listaTemporariaDeEtapas.addAll(listaDeEtapasOriginal);

		List<List<GuardaEtapaProcesso>> listaParteDaManha = getPossibilidades(listaTemporariaDeEtapas, diasPossiveis,
				true);

		for (List<GuardaEtapaProcesso> list : listaParteDaManha)
			listaTemporariaDeEtapas.removeAll(list);

		List<List<GuardaEtapaProcesso>> listaParteDaTarde = getPossibilidades(listaTemporariaDeEtapas, diasPossiveis,
				false);

		for (List<GuardaEtapaProcesso> list : listaParteDaTarde)
			listaTemporariaDeEtapas.removeAll(list);

		if (!listaTemporariaDeEtapas.isEmpty()) {

			List<GuardaEtapaProcesso> listaDeAgendamento = new ArrayList<GuardaEtapaProcesso>();

			for (List<GuardaEtapaProcesso> lista : listaParteDaTarde) {

				int totalTime = getTempoTotalDasEtapas(lista);

				for (GuardaEtapaProcesso etapaProcesso : lista) {
					int ConversationTime = etapaProcesso.getTempoExecucao();

					if (ConversationTime + totalTime <= 240) {
						lista.add(etapaProcesso);
						etapaProcesso.setAgendada(true);
						listaDeAgendamento.add(etapaProcesso);
					}
				}

				listaTemporariaDeEtapas.removeAll(listaDeAgendamento);
				if (listaTemporariaDeEtapas.isEmpty())
					break;
			}
		}

		if (!listaTemporariaDeEtapas.isEmpty()) {
			Dialogo.showMenssageErro("N?o foi poss?vel agendar todas as tarefas.");
			return null;
		}

		return getListaDeAgendamento(listaParteDaManha, listaParteDaTarde);
	}

	@SuppressWarnings("deprecation")
	private List<List<GuardaEtapaProcesso>> getListaDeAgendamento(List<List<GuardaEtapaProcesso>> listParteManha,
			List<List<GuardaEtapaProcesso>> listParteTarde) {

		int totalDiasPossiveis = listParteManha.size();

		List<String> listaFinal = new ArrayList<String>();

		for (int dia = 0; dia < totalDiasPossiveis; dia++) {
			List<GuardaEtapaProcesso> listaTemporaria = new ArrayList<GuardaEtapaProcesso>();

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm ");
			date.setHours(9);
			date.setMinutes(0);
			date.setSeconds(0);

			int numLinhaDeMontagem = dia + 1;
			String horario = dateFormat.format(date);

			System.out.println("LINHA DE MONTAGEM " + numLinhaDeMontagem + ":");
			listaFinal.add("\n LINHA DE MONTAGEM" + Integer.toString(dia) + ": \n");

			List<GuardaEtapaProcesso> listManha = listParteManha.get(dia);
			for (GuardaEtapaProcesso etapa : listManha) {
				etapa.setHorario(horario);
				System.out.println(horario + etapa.getDescricao());
				listaFinal.add(horario + etapa.getDescricao() + "\n");

				horario = getProximoAgendamento(date, etapa.getTempoExecucao());
				listaTemporaria.add(etapa);
			}

			int tempoAlmoco = 60;
			GuardaEtapaProcesso gAlmoco = new GuardaEtapaProcesso();
			gAlmoco.setDescricao("Almo?o");
			gAlmoco.setTempoExecucao(60);

			gAlmoco.setHorario(horario);
			listaTemporaria.add(gAlmoco);
			System.out.println(horario + "Almo?o");
			listaFinal.add(horario + "Almo?o \n");

			horario = getProximoAgendamento(date, tempoAlmoco);
			List<GuardaEtapaProcesso> listTarde = listParteTarde.get(dia);
			for (GuardaEtapaProcesso etapa : listTarde) {
				etapa.setHorario(horario);
				listaTemporaria.add(etapa);
				System.out.println(horario + etapa.getDescricao());
				listaFinal.add(horario + etapa.getDescricao() + "\n");

				horario = getProximoAgendamento(date, etapa.getTempoExecucao());
			}

			GuardaEtapaProcesso gGinastica = new GuardaEtapaProcesso();
			gGinastica.setDescricao("Gin?stica Laboral");
			gGinastica.setTempoExecucao(60);

			gGinastica.setHorario(horario);
			listaTemporaria.add(gGinastica);
			System.out.println(horario + gGinastica.getDescricao() + "\n");
			listaFinal.add(horario + gGinastica.getDescricao() + "\n");

			datedConversationsList.add(listaTemporaria);
		}

		Dialogo.showMenssagem(listaFinal);

		return datedConversationsList;

	}

	private String getProximoAgendamento(Date date, int timeDuration) {
		long timeInLong = date.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm ");

		long tempoTotal = timeDuration * 60 * 1000;
		tempoTotal = timeInLong + tempoTotal;

		date.setTime(tempoTotal);
		String str = dateFormat.format(date);
		return str;
	}

	private List<List<GuardaEtapaProcesso>> getPossibilidades(List<GuardaEtapaProcesso> listEtapas,
			int totalDiaPossiveis, boolean isTurnoDaManha) {

		int tempoLimiteMinimo = 180;
		int tempoLimiteMaximo = 240;

		if (isTurnoDaManha)
			tempoLimiteMaximo = tempoLimiteMinimo;

		int totalEtapas = listEtapas.size();
		List<List<GuardaEtapaProcesso>> listaCombinacoes = new ArrayList<List<GuardaEtapaProcesso>>();
		int totalDeCombinacoes = 0;

		for (int count = 0; count < totalEtapas; count++) {

			int inicio = count;
			int totalTime = 0;

			List<GuardaEtapaProcesso> listaCombinacaoTmp = new ArrayList<GuardaEtapaProcesso>();

			while (inicio != totalEtapas) {
				int currentCount = inicio;
				inicio++;
				GuardaEtapaProcesso etapa = listEtapas.get(currentCount);
				if (etapa.isAgendada())
					continue;
				int tempo = etapa.getTempoExecucao();
				if (tempo > tempoLimiteMaximo || tempo + totalTime > tempoLimiteMaximo)
					continue;

				listaCombinacaoTmp.add(etapa);
				totalTime += tempo;

				if (isTurnoDaManha) {
					if (totalTime == tempoLimiteMaximo)
						break;
				} else if (totalTime >= tempoLimiteMinimo)
					break;
			}

			boolean valido = false;
			if (isTurnoDaManha)
				valido = (totalTime == tempoLimiteMaximo);
			else
				valido = (totalTime >= tempoLimiteMinimo && totalTime <= tempoLimiteMaximo);

			if (valido) {

				listaCombinacoes.add(listaCombinacaoTmp);
				for (GuardaEtapaProcesso Conversation : listaCombinacaoTmp)
					Conversation.setAgendada(true);

				totalDeCombinacoes++;
				if (totalDeCombinacoes == totalDiaPossiveis)
					break;
			}
		}

		return listaCombinacoes;
	}

	public Optional<List<GuardaLinhaDeMontagem>> gerarLinhasDeMontagem(List<GuardaEtapaProcesso> arquivo) {

		List<GuardaLinhaDeMontagem> listaDeMontagem = new ArrayList<>();

		getAgendamento(arquivo);

		return Optional.ofNullable(listaDeMontagem);
	}

//	public void configurarHorarioDeTrabalhoPadrao() {
//
//		if (linhaDeMontagem.getHorarioRotinaTrabalho().isEmpty()) {
//			List<GuardaHorarioDeRotinaTrabalho> listaDeHorarios = new ArrayList<>();
//
//			GuardaHorarioDeRotinaTrabalho gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
//			gHorarioRotina.setInicio(new Time(9 * 3600));
//			gHorarioRotina.setFim(new Time(12 * 3600));
//			gHorarioRotina.setDescricao("Per?odo da manh?");
//			listaDeHorarios.add(gHorarioRotina);
//
//			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
//			gHorarioRotina.setInicio(new Time(12 * 3600));
//			gHorarioRotina.setFim(new Time(13 * 3600));
//			gHorarioRotina.setDescricao("Per?odo do Almo?o");
//			gHorarioRotina.setDescanco(true);
//
//			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
//			gHorarioRotina.setInicio(new Time(13 * 3600));
//			gHorarioRotina.setFim(new Time(17 * 3600));
//			gHorarioRotina.setDescricao("Per?odo da Tarde");
//
//			gHorarioRotina = new GuardaHorarioDeRotinaTrabalho();
//			gHorarioRotina.setInicio(new Time(16 * 3600));
//			gHorarioRotina.setFim(new Time(1 * 3600));
//			gHorarioRotina.setDescricao("Atividades de Gin?stica");
//			gHorarioRotina.setDescanco(true);
//
//			listaDeHorarios.add(gHorarioRotina);
//
//			linhaDeMontagem.setHorarioRotinaTrabalho(listaDeHorarios);
//		}
//
//	}

}
