package br.com.sistema.academico.controller;

import javax.swing.*;
import java.awt.*;

public abstract class TelaCadastroTemplate extends JPanel {
    protected final String titulo;
    
    public TelaCadastroTemplate(String titulo) {
        this.titulo = titulo;
        inicializarComponentes();
        inicializarInterface();
    }

    protected void inicializarComponentes() {
        // Template method para inicialização de componentes
    }

    // Template Method principal
    private void inicializarInterface() {
        setLayout(new BorderLayout());
        configurarTitulo();
        add(criarFormulario(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);
        configurarEstilo();
    }

    // Métodos abstratos que devem ser implementados pelas subclasses
    protected abstract JPanel criarFormulario();
    protected abstract JPanel criarBotoes();

    // Métodos utilitários para as subclasses
    protected void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarMensagemAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    protected boolean confirmarAcao(String mensagem) {
        return JOptionPane.showConfirmDialog(this, 
                                           mensagem, 
                                           "Confirmação", 
                                           JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // Método template para configurar o título
    private void configurarTitulo() {
        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(tituloLabel, BorderLayout.NORTH);
    }

    // Método template para configurar o estilo
    private void configurarEstilo() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);
    }
}