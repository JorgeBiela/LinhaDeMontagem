package modulo.sistema;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Dialogo {

	public static void showMenssageErro(String mensagem) {

		// TODO PADRONIZAR NO HTML DEPOIS
		JOptionPane.showMessageDialog(null, mensagem, null, 2, null);
	}

	public static void showMenssagem(List<String> listaFinal) {
		JOptionPane pane = new JOptionPane(listaFinal);

		// TODO DEIXAR RESPONSIVO
		JDialog dialog = pane.createDialog(null, "Resultados");
		dialog.setSize(500, 600);
		dialog.setVisible(true);

	}

}
