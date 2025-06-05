package main;

import controller.TelaCadastroAluno;
import controller.TelaListaAlunos;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema Acadêmico");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Painéis de conteúdo (telas)
            JPanel contentPanel = new JPanel(new CardLayout());
            TelaCadastroAluno cadastroAluno = new TelaCadastroAluno();
            TelaListaAlunos listaAlunos = new TelaListaAlunos();
            JPanel telaTurmas = new JPanel(); telaTurmas.add(new JLabel("Cadastro de Turmas"));
            JPanel telaProfessores = new JPanel(); telaProfessores.add(new JLabel("Cadastro de Professores"));

            contentPanel.add(cadastroAluno, "aluno");
            contentPanel.add(telaTurmas, "turmas");
            contentPanel.add(telaProfessores, "professores");
            contentPanel.add(listaAlunos, "lista");

            // Menu lateral estilo Bootstrap
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
            menuPanel.setBackground(new Color(33, 37, 41));

            String[] opcoes = {
                "Cadastro de Aluno", 
                "Cadastro de Turmas", 
                "Cadastro de Professores", 
                "Lista de Alunos", 
                "Sair"
            };

            for (String nome : opcoes) {
                JButton botao = new JButton(nome);
                botao.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao.setMaximumSize(new Dimension(180, 40));
                botao.setForeground(Color.WHITE);
                botao.setBackground(new Color(52, 58, 64));
                botao.setFocusPainted(false);
                botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                botao.setFont(new Font("SansSerif", Font.PLAIN, 14));

                switch (nome) {
                    case "Cadastro de Aluno":
                        botao.addActionListener(e -> ((CardLayout) contentPanel.getLayout()).show(contentPanel, "aluno"));
                        break;
                    case "Cadastro de Turmas":
                        botao.addActionListener(e -> ((CardLayout) contentPanel.getLayout()).show(contentPanel, "turmas"));
                        break;
                    case "Cadastro de Professores":
                        botao.addActionListener(e -> ((CardLayout) contentPanel.getLayout()).show(contentPanel, "professores"));
                        break;
                    case "Lista de Alunos":
                        botao.addActionListener(e -> {
                            // Recarrega a lista sempre que for exibida
                            contentPanel.remove(listaAlunos);
                            TelaListaAlunos novaLista = new TelaListaAlunos();
                            contentPanel.add(novaLista, "lista");
                            ((CardLayout) contentPanel.getLayout()).show(contentPanel, "lista");
                        });
                        break;
                    case "Sair":
                        botao.addActionListener(e -> System.exit(0));
                        break;
                }

                menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                menuPanel.add(botao);
            }

            menuPanel.setPreferredSize(new Dimension(220, frame.getHeight()));

            frame.setLayout(new BorderLayout());
            frame.add(menuPanel, BorderLayout.WEST);
            frame.add(contentPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
