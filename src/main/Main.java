package main;

import javax.swing.*;
import java.awt.*;

import controller.TelaCadastroAluno;
import controller.TelaCadastroProfessor;
import controller.TelaCadastroTurma;
import controller.TelaCadastroDisciplina;
import controller.TelaMatriculaCompleta;
import controller.TelaLancamentoNotas;
import controller.TelaBoletimAluno;
import controller.TelaLogin;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema Acadêmico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1));
        menuPanel.setPreferredSize(new Dimension(200, frame.getHeight()));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JButton btnAlunos = new JButton("Cadastro de Alunos");
        btnAlunos.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(TelaCadastroAluno.getInstancia(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        
        

        JButton btnProfessores = new JButton("Cadastro de Professores");
        btnProfessores.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroProfessor(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnTurmas = new JButton("Cadastro de Turmas");
        btnTurmas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroTurma(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnDisciplinas = new JButton("Cadastro de Disciplinas");
        btnDisciplinas.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaCadastroDisciplina(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnMatriculas = new JButton("Matrícula");
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

        JButton btnBoletim = new JButton("Boletim do Aluno");
        btnBoletim.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(new TelaBoletimAluno(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        JButton btnLogout = new JButton("Sair");
        btnLogout.addActionListener(e -> {
            frame.dispose();
            TelaLogin.getInstancia().setVisible(true);
        });

        menuPanel.add(btnAlunos);
        menuPanel.add(btnProfessores);
        menuPanel.add(btnTurmas);
        menuPanel.add(btnDisciplinas);
        menuPanel.add(btnMatriculas);
        menuPanel.add(btnNotas);
        menuPanel.add(btnBoletim);
        menuPanel.add(btnLogout);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, contentPanel);
        splitPane.setDividerLocation(200);

        frame.setContentPane(splitPane);
        frame.setVisible(true);
    }
}
