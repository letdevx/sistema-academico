package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.service.MatriculaDisciplinaService;

public class TelaMatriculaDisciplina extends JPanel {

    private JComboBox<String> comboAlunos;
    private JComboBox<String> comboTurmas;
    private JList<String> listaDisciplinas;
    private final DefaultTableModel tableModel;

    private final MatriculaDisciplinaService matriculaDisciplinaService = new MatriculaDisciplinaService();

    public TelaMatriculaDisciplina() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Matrícula em Disciplinas"));
        formPanel.setBackground(new Color(240, 255, 250));

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
        formPanel.add(new JLabel("Disciplinas da Turma:"), gbc);
        gbc.gridx = 1;
        listaDisciplinas = new JList<>();
        listaDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaDisciplinas.setVisibleRowCount(5);
        formPanel.add(new JScrollPane(listaDisciplinas), gbc);

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
            listaDisciplinas.clearSelection();
        });
        formPanel.add(limpar, gbc);

        // Tabela com as matrículas
        String[] colunas = {"Aluno", "Turma", "Disciplinas"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane tabelaScroll = new JScrollPane(tabela);
        tabelaScroll.setBorder(BorderFactory.createTitledBorder("Disciplinas Matriculadas"));

        comboTurmas.addActionListener(e -> atualizarDisciplinas());

        carregarAlunos();
        carregarTurmas();
        carregarMatriculas();

        add(formPanel, BorderLayout.NORTH);
        add(tabelaScroll, BorderLayout.CENTER);
    }

    private void carregarAlunos() {
        comboAlunos.removeAllItems();
        java.io.File file = new java.io.File("src/main/resources/data/alunos.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length > 1) {
                    comboAlunos.addItem(dados[1] + " - " + dados[0]);
                }
            }
        } catch (IOException | RuntimeException e) {
            comboAlunos.addItem("Erro ao carregar alunos");
        }
    }

    private void carregarTurmas() {
        comboTurmas.removeAllItems();
        java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 1) {
                    comboTurmas.addItem(dados[0]);
                }
            }
        } catch (IOException | RuntimeException e) {
            comboTurmas.addItem("Erro ao carregar turmas");
        }
    }

    private void atualizarDisciplinas() {
        String turmaSelecionada = (String) comboTurmas.getSelectedItem();
        if (turmaSelecionada == null) return;
        java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5 && dados[0].equals(turmaSelecionada)) {
                    String[] disciplinas = dados[4].split(",");
                    listaDisciplinas.setListData(disciplinas);
                    return;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar disciplinas da turma.");
        }
    }

    private void salvarMatricula() {
        String aluno = (String) comboAlunos.getSelectedItem();
        String turma = (String) comboTurmas.getSelectedItem();
        List<String> disciplinas = listaDisciplinas.getSelectedValuesList();
        if (aluno == null || turma == null || disciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione aluno, turma e ao menos uma disciplina.");
            return;
        }
        String cpf = aluno.split(" - ")[1];
        try {
            matriculaDisciplinaService.validarCampos(cpf, turma, disciplinas);
            matriculaDisciplinaService.salvarMatricula(cpf, turma, disciplinas);
            String disciplinasString = String.join(",", disciplinas);
            tableModel.addRow(new String[]{cpf, turma, disciplinasString});
            JOptionPane.showMessageDialog(this, "Matrícula em disciplinas realizada!");
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao salvar matrícula: " + e.getMessage());
        }
    }

    private void carregarMatriculas() {
        tableModel.setRowCount(0);
        java.io.File file = new java.io.File("src/main/resources/data/matriculas_disciplinas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    tableModel.addRow(dados);
                }
            }
        } catch (IOException | RuntimeException e) {
            // ok se ainda não existir ou erro de leitura
        }
    }
}

