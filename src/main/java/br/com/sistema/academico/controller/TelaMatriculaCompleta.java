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

import br.com.sistema.academico.model.Aluno;
import br.com.sistema.academico.service.AlunoService;
import br.com.sistema.academico.service.MatriculaAlunoService;
import br.com.sistema.academico.service.MatriculaDisciplinaService;

public class TelaMatriculaCompleta extends JPanel {

    private final JComboBox<String> comboAlunos;
    private final JComboBox<String> comboTurmas;
    private final JList<String> listaDisciplinas;
    private final DefaultTableModel tableModel;

    private final AlunoService alunoService = new AlunoService();
    private final MatriculaAlunoService matriculaAlunoService = new MatriculaAlunoService();
    private final MatriculaDisciplinaService matriculaDisciplinaService = new MatriculaDisciplinaService();

    public TelaMatriculaCompleta() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Matrícula Completa (Turma + Disciplinas)"));
        formPanel.setBackground(Color.LIGHT_GRAY); // cinza claro padronizado igual às outras telas

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Aluno
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        comboAlunos = new JComboBox<>();
        formPanel.add(comboAlunos, gbc);

        // Turma
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Turma:"), gbc);
        gbc.gridx = 1;
        comboTurmas = new JComboBox<>();
        comboTurmas.addActionListener(e -> atualizarDisciplinas());
        formPanel.add(comboTurmas, gbc);

        // Disciplinas
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Disciplinas:"), gbc);
        gbc.gridx = 1;
        listaDisciplinas = new JList<>();
        listaDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaDisciplinas.setVisibleRowCount(5);
        formPanel.add(new JScrollPane(listaDisciplinas), gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        JButton btnMatricular = new JButton("Matricular");
        btnMatricular.addActionListener(e -> realizarMatricula());
        formPanel.add(btnMatricular, gbc);

        gbc.gridx = 1;
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        formPanel.add(btnLimpar, gbc);

        // Tabela
        String[] colunas = {"Aluno (CPF)", "Turma", "Disciplinas"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane tabelaScroll = new JScrollPane(tabela);
        tabelaScroll.setBorder(BorderFactory.createTitledBorder("Matrículas Realizadas"));

        carregarAlunos();
        carregarTurmas();
        carregarMatriculas();

        add(formPanel, BorderLayout.NORTH);
        add(tabelaScroll, BorderLayout.CENTER);
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
                String[] dados = linha.split(";");
                if (dados.length >= 1) {
                    comboTurmas.addItem(dados[0]);
                }
            }
        } catch (IOException e) {
            comboTurmas.addItem("Erro ao carregar turmas");
        } catch (RuntimeException e) {
            comboTurmas.addItem("Erro inesperado ao carregar turmas");
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

    private void realizarMatricula() {
        String aluno = (String) comboAlunos.getSelectedItem();
        String turma = (String) comboTurmas.getSelectedItem();
        List<String> disciplinas = listaDisciplinas.getSelectedValuesList();
        if (aluno == null || turma == null || disciplinas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione aluno, turma e ao menos uma disciplina.");
            return;
        }
        String cpf = aluno.split(" - ")[1];
        String disciplinasString = String.join(",", disciplinas);
        try {
            // Validação e matrícula na turma
            matriculaAlunoService.validarCampos(cpf, turma);
            matriculaAlunoService.salvarMatricula(cpf, turma);
            // Validação e matrícula nas disciplinas
            matriculaDisciplinaService.validarCampos(cpf, turma, disciplinas);
            matriculaDisciplinaService.salvarMatricula(cpf, turma, disciplinas);
            tableModel.addRow(new String[]{cpf, turma, disciplinasString});
            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula: " + e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao salvar matrícula");
        }
    }

    private void limparCampos() {
        comboAlunos.setSelectedIndex(0);
        comboTurmas.setSelectedIndex(0);
        listaDisciplinas.clearSelection();
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
            // tudo bem se não existir ou erro de leitura
        }
    }
}
