package controller;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Login com divisão ao meio, Singleton, Template Method
 * Imagem à esquerda, campos à direita
 */
public class TelaLogin extends LoginTemplate {

    private static TelaLogin instancia;

    public static TelaLogin getInstancia() {
        if (instancia == null) {
            instancia = new TelaLogin();
        }
        return instancia;
    }

    private TelaLogin() {
        super("Login - Sistema Acadêmico");

        // --- Painel com imagem à esquerda ---
        JLabel imagemLabel = new JLabel();
        imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Carrega e redimensiona a imagem
        ImageIcon originalIcon = new ImageIcon("/Users/letdevx/Documents/sistema_academico/sistema-academico/src/logo.png"); // troque pelo caminho correto
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));

        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.add(imagemLabel, BorderLayout.CENTER);

        // --- Painel de login à direita ---
        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");
        campoUsuario = new JTextField(15);
        campoSenha = new JPasswordField(15);
        JButton btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> autenticar());

        gbc.gridx = 0; gbc.gridy = 0;
        painelLogin.add(labelUsuario, gbc);
        gbc.gridx = 1;
        painelLogin.add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelLogin.add(labelSenha, gbc);
        gbc.gridx = 1;
        painelLogin.add(campoSenha, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelLogin.add(btnEntrar, gbc);

        // --- Divide a tela ---
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelImagem, painelLogin);
        split.setDividerLocation(400);
        add(split);
    }

    @Override
    protected boolean validar(String usuario, String senha) {
        return usuario.equals("admin") && senha.equals("123");
    }
}
