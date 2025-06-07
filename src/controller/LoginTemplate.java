package controller;

import javax.swing.*;

public abstract class LoginTemplate extends JFrame {

    protected JTextField campoUsuario;
    protected JPasswordField campoSenha;

    public LoginTemplate(String titulo) {
        super(titulo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 780);
        setLocationRelativeTo(null);
    }

    protected final void autenticar() {
        String usuario = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        if (validar(usuario, senha)) {
            exibirCarregamento();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
        }
    }

    protected abstract boolean validar(String usuario, String senha);

    protected void exibirCarregamento() {
        JDialog loadingDialog = new JDialog(this, "Carregando...", true);
        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true);
        loadingDialog.add(barra);
        loadingDialog.setSize(300, 100);
        loadingDialog.setLocationRelativeTo(this);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            loadingDialog.dispose();
            this.dispose();
            main.Main.main(null);
        }).start();

        loadingDialog.setVisible(true);
    }
}
