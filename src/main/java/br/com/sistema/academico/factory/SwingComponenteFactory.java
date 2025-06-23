package br.com.sistema.academico.factory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SwingComponenteFactory implements ComponenteFactory {
    @Override
    public JTextField criarCampoTexto(Boolean longo) {
        if (longo) {
            return new JTextField(50);
        }
        else {
            return new JTextField(20);
        }
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
