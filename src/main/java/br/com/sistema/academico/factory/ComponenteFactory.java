package br.com.sistema.academico.factory;

import javax.swing.*;

public interface ComponenteFactory {
    JTextField criarCampoTexto();
    JButton criarBotao(String texto);
    JLabel criarLabel(String texto);
}