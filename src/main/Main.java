package main;

import controller.TelaCadastroAluno;
import controller.TelaCadastroProfessor;
import controller.TelaListaAlunos;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Sistema Acadêmico Universitário");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(1000, 600);
            janela.setLocationRelativeTo(null);

            JPanel painelPrincipal = new JPanel(new BorderLayout());

            // Sidebar moderna
            JPanel menuLateral = new JPanel();
            menuLateral.setLayout(new GridLayout(0, 1, 10, 10));
            menuLateral.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
            menuLateral.setBackground(new Color(33, 37, 41)); // cor escura tipo Bootstrap

            Font fonteMenu = new Font("Segoe UI", Font.BOLD, 14);
            Color corTexto = Color.WHITE;

            JButton btnCadastroAluno = new JButton("Cadastrar Aluno");
            JButton btnCadastroProfessor = new JButton("Cadastrar Professor");
            JButton btnListaAlunos = new JButton("Lista de Alunos");

            JButton[] botoes = { btnCadastroAluno, btnCadastroProfessor, btnListaAlunos };

            for (JButton btn : botoes) {
                btn.setFocusPainted(false);
                btn.setBackground(new Color(52, 58, 64)); // estilo btn-dark
                btn.setForeground(corTexto);
                btn.setFont(fonteMenu);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                menuLateral.add(btn);
            }

            JPanel painelConteudo = new JPanel(new BorderLayout());

            btnCadastroAluno.addActionListener(e -> {
                painelConteudo.removeAll();
                painelConteudo.add(new TelaCadastroAluno(), BorderLayout.CENTER);
                painelConteudo.revalidate();
                painelConteudo.repaint();
            });

            btnCadastroProfessor.addActionListener(e -> {
                painelConteudo.removeAll();
                painelConteudo.add(new TelaCadastroProfessor(), BorderLayout.CENTER);
                painelConteudo.revalidate();
                painelConteudo.repaint();
            });

            btnListaAlunos.addActionListener(e -> {
                painelConteudo.removeAll();
                painelConteudo.add(new TelaListaAlunos(), BorderLayout.CENTER);
                painelConteudo.revalidate();
                painelConteudo.repaint();
            });

            painelPrincipal.add(menuLateral, BorderLayout.WEST);
            painelPrincipal.add(painelConteudo, BorderLayout.CENTER);

            janela.add(painelPrincipal);
            janela.setVisible(true);
        });
    }
}
