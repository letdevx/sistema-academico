package controller;

import javax.swing.*;
import java.awt.*;
import main.Main;

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

        // Painel da imagem (lado esquerdo)
        JLabel imagemLabel = new JLabel();
        imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Carrega e redimensiona a imagem
        ImageIcon imagemOriginal = new ImageIcon("login_image.png"); // <- nome do arquivo da imagem
        Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(500, 600, Image.SCALE_SMOOTH);
        imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));

        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.add(imagemLabel, BorderLayout.CENTER);

        // Painel do formulário de login (lado direito)
        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel logo = new JLabel("<html><center><h1 style='color:#003366;'>SISTEMA<br>ACADÊMICO</h1><p>TECHNOVA UNIVERSITY</p></center></html>", SwingConstants.CENTER);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));

        campoUsuario = new JTextField(16);
        campoSenha = new JPasswordField(16);
        JButton btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBackground(new Color(0, 123, 255)); // cor azul Bootstrap
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setOpaque(true);
        btnEntrar.setBorderPainted(false);
        btnEntrar.addActionListener(e -> autenticar());


        JLabel lblUsuario = new JLabel("Usuário:");
        JLabel lblSenha = new JLabel("Senha:");
        JLabel esqueciSenha = new JLabel("Esqueceu a senha?");
        esqueciSenha.setHorizontalAlignment(SwingConstants.CENTER);
        esqueciSenha.setForeground(Color.GRAY);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painelLogin.add(logo, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        painelLogin.add(lblUsuario, gbc);
        gbc.gridx = 1;
        painelLogin.add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelLogin.add(lblSenha, gbc);
        gbc.gridx = 1;
        painelLogin.add(campoSenha, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        painelLogin.add(btnEntrar, gbc);

        gbc.gridy++;
        painelLogin.add(esqueciSenha, gbc);

        // Divide a tela em imagem (esquerda) e login (direita)
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelImagem, painelLogin);
        split.setDividerLocation(500);
        split.setDividerSize(0);
        split.setEnabled(false);

        add(split);
    }

    @Override
    protected boolean validar(String usuario, String senha) {
        return usuario.equals("admin") && senha.equals("123");
    }
}
