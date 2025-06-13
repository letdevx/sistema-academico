package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.model.Aluno;
import br.com.sistema.academico.service.AlunoService;
import br.com.sistema.academico.service.MatriculaAlunoService;

public class TelaMatriculaAluno extends JPanel {

    private JComboBox<String> comboAlunos;
    private JComboBox<String> comboTurmas;
    private final DefaultTableModel tableModel;

    private final MatriculaAlunoService matriculaAlunoService = new MatriculaAlunoService();
    private final AlunoService alunoService = new AlunoService();

    public TelaMatriculaAluno() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Matrícula de Aluno"));
        formPanel.setBackground(new Color(230, 255, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        comboAlunos = new JComboBox<>();
        formPanel.add(comboAlunos, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        comboTurmas = new JComboBox<>();
        formPanel.add(comboTurmas, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton matricular = new JButton("Matricular");
        matricular.addActionListener(e -> salvarMatricula());
        formPanel.add(matricular, gbc);

        gbc.gridx = 1;
        JButton limpar = new JButton("Limpar");
        limpar.addActionListener(e -> {
            comboAlunos.setSelectedIndex(0);
            comboTurmas.setSelectedIndex(0);
        });
        formPanel.add(limpar, gbc);

        String[] colunas = {"CPF do Aluno", "Turma"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Alunos Matriculados"));

        carregarAlunos();
        carregarTurmas();
        carregarMatriculas();

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void carregarAlunos() {
        comboAlunos.removeAllItems();
        for (Aluno aluno : alunoService.listarAlunos()) {
            comboAlunos.addItem(aluno.getCpf() + " - " + aluno.getNome());
        }
    }

    private void carregarTurmas() {
        comboTurmas.removeAllItems();
        java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                comboTurmas.addItem(linha);
            }
        } catch (Exception e) {
            comboTurmas.addItem("Erro ao carregar turmas");
        }
    }

    private void salvarMatricula() {
        String aluno = (String) comboAlunos.getSelectedItem();
        String turma = (String) comboTurmas.getSelectedItem();
        if (aluno == null || turma == null) return;
        String cpf = aluno.split(" - ")[0];
        try {
            matriculaAlunoService.validarCampos(cpf, turma);
            matriculaAlunoService.salvarMatricula(cpf, turma);
            tableModel.addRow(new String[]{cpf, turma});
            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException | RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula: " + e.getMessage());
        }
    }

    private void carregarMatriculas() {
        tableModel.setRowCount(0);
        java.io.File file = new java.io.File("src/main/resources/data/matriculas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                tableModel.addRow(linha.split(";"));
            }
        } catch (Exception e) {
            // arquivo ainda não existe ou erro de leitura
        }
    }
}
