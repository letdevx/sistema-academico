package br.com.sistema.academico.factory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface ComponenteFactory {
    JTextField criarCampoTexto(Boolean longo);
    JButton criarBotao(String texto);
    JLabel criarLabel(String texto);
}