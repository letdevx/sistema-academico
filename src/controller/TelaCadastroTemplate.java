package controller;

import javax.swing.*;
import java.awt.*;

public abstract class TelaCadastroTemplate extends JPanel {
    public TelaCadastroTemplate(String titulo) {
        setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        add(tituloLabel, BorderLayout.NORTH);
        add(criarFormulario(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);
    }

    protected abstract JPanel criarFormulario();
    protected abstract JPanel criarBotoes();
}