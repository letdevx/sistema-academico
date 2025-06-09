package controller;

import javax.swing.*;
import java.awt.*;
import main.Main; // Importa corretamente a classe principal

/**
 * Classe abstrata base para telas de login.
 * Aplica o padrão Template Method.
 */
public abstract class LoginTemplate extends JFrame {

    protected JTextField campoUsuario;
    protected JPasswordField campoSenha;

    public LoginTemplate(String titulo) {
        super(titulo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Método final que define o fluxo de autenticação.
     * As subclasses implementam apenas a validação.
     */
    protected final void autenticar() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        if (validar(usuario, senha)) {
            exibirCarregamento();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
        }
    }

    /**
     * Método abstrato a ser implementado pelas subclasses (ex: TelaLogin)
     */
    protected abstract boolean validar(String usuario, String senha);

    /**
     * Mostra barra de carregamento e chama o sistema principal
     */
    protected void exibirCarregamento() {
        JDialog loadingDialog = new JDialog(this, "Carregando...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 30));
        loadingDialog.add(BorderLayout.CENTER, progressBar);
        loadingDialog.setSize(350, 100);
        loadingDialog.setLocationRelativeTo(this);

        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simula carregamento
            } catch (InterruptedException ignored) {}
            loadingDialog.dispose(); // Fecha carregamento
            this.dispose(); // Fecha tela de login
            SwingUtilities.invokeLater(() -> Main.main(new String[0])); // Abre tela principal
        }).start();

        loadingDialog.setVisible(true);
    }
}
