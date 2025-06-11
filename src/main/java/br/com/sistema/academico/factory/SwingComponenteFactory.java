package br.com.sistema.academico.factory;

import javax.swing.*;

public class SwingComponenteFactory implements ComponenteFactory {
    @Override
    public JTextField criarCampoTexto() {
        return new JTextField(20);
    }

    @Override
    public JButton criarBotao(String texto) {
        return new JButton(texto);
    }

    @Override
    public JLabel criarLabel(String texto) {
        return new JLabel(texto);
    }
}
