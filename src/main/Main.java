package main;

import javax.swing.*;
import controller.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema Acadêmico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1));
        menuPanel.setPreferredSize(new Dimension(200, frame.getHeight()));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JButton btnAlunos = new JButton("Alunos");
        btnAlunos.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroAluno(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnProfessores = new JButton("Professores");
        btnProfessores.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroProfessor(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnTurmas = new JButton("Turmas");
        btnTurmas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroTurma(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnDisciplinas = new JButton("Disciplinas");
        btnDisciplinas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroDisciplina(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnMatriculas = new JButton("Matrículas");
        btnMatriculas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaMatriculaCompleta(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnNotas = new JButton("Lançamento de Notas");
        btnNotas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaLancamentoNotas(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnBoletim = new JButton("Boletim");
        btnBoletim.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaBoletimAluno(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        // ➕ Botão de logout
        JButton btnLogout = new JButton("Sair");
        btnLogout.addActionListener(e -> {
            frame.dispose(); // Fecha a janela principal
            TelaLogin.getInstancia().setVisible(true); // Retorna para a tela de login
        });

        menuPanel.add(btnAlunos);
        menuPanel.add(btnProfessores);
        menuPanel.add(btnTurmas);
        menuPanel.add(btnDisciplinas);
        menuPanel.add(btnMatriculas);
        menuPanel.add(btnNotas);
        menuPanel.add(btnBoletim);
        menuPanel.add(btnLogout); // ← Adicionado ao final

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, contentPanel);
        splitPane.setDividerLocation(200);

        frame.setContentPane(splitPane);
        frame.setVisible(true);
    }
}
