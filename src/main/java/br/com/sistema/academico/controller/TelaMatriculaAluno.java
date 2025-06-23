package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
    private DefaultTableModel tableModel;
    private JTable tabelaMatriculas;
    private int matriculaEditando = -1;

    private final MatriculaAlunoService matriculaAlunoService = new MatriculaAlunoService();
    private final AlunoService alunoService = new AlunoService();

    public TelaMatriculaAluno() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY); // cinza claro igual às outras telas
        add(criarFormulario(), BorderLayout.NORTH);
        add(criarTabela(), BorderLayout.CENTER);
        add(criarBotoes(), BorderLayout.SOUTH);
        carregarAlunos();
        carregarTurmas();
        carregarMatriculas();
    }

    private JPanel criarFormulario() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Matrícula de Aluno"));
        formPanel.setBackground(Color.LIGHT_GRAY); // cinza claro padronizado

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
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

        return formPanel;
    }

    private JScrollPane criarTabela() {
        String[] colunas = {"CPF do Aluno", "Turma"};
        tableModel = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaMatriculas = new JTable(tableModel);
        tabelaMatriculas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaMatriculas.setRowHeight(28);
        tabelaMatriculas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaMatriculas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tabelaMatriculas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Alunos Matriculados"));
        return scrollPane;
    }

    private JPanel criarBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Matricular");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        // Bootstrap Azul
        btnSalvar.setBackground(new Color(13, 110, 253));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFont(btnSalvar.getFont().deriveFont(Font.BOLD));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(13, 110, 253)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setOpaque(true);
        btnSalvar.setContentAreaFilled(true);
        // Bootstrap Amarelo
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(btnEditar.getFont().deriveFont(Font.BOLD));
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(255, 193, 7)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setOpaque(true);
        btnEditar.setContentAreaFilled(true);
        // Bootstrap Vermelho
        btnExcluir.setBackground(new Color(220, 53, 69));
        btnExcluir.setForeground(Color.BLACK);
        btnExcluir.setFont(btnExcluir.getFont().deriveFont(Font.BOLD));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(220, 53, 69)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setOpaque(true);
        btnExcluir.setContentAreaFilled(true);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        btnSalvar.addActionListener(e -> salvarMatricula());
        btnEditar.addActionListener(e -> editarMatricula());
        btnExcluir.addActionListener(e -> excluirMatricula());
        return painelBotoes;
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
            if (matriculaEditando >= 0) {
                atualizarMatriculaArquivo(matriculaEditando, cpf, turma);
                tableModel.setValueAt(cpf, matriculaEditando, 0);
                tableModel.setValueAt(turma, matriculaEditando, 1);
                JOptionPane.showMessageDialog(this, "Matrícula editada com sucesso!");
                matriculaEditando = -1;
            } else {
                matriculaAlunoService.salvarMatricula(cpf, turma);
                tableModel.addRow(new String[]{cpf, turma});
                JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException | RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula: " + e.getMessage());
        }
    }

    private void editarMatricula() {
        int row = tabelaMatriculas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para editar.");
            return;
        }
        comboAlunos.setSelectedItem(tableModel.getValueAt(row, 0));
        comboTurmas.setSelectedItem(tableModel.getValueAt(row, 1));
        matriculaEditando = row;
    }

    private void excluirMatricula() {
        int row = tabelaMatriculas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para excluir.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta matrícula?", "Confirmação", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        removerMatriculaArquivo(row);
        tableModel.removeRow(row);
        matriculaEditando = -1;
        JOptionPane.showMessageDialog(this, "Matrícula excluída com sucesso!");
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

    private void atualizarMatriculaArquivo(int row, String cpf, String turma) throws IOException {
        java.io.File file = new java.io.File("src/main/resources/data/matriculas.txt");
        java.util.List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
        if (row >= 0 && row < linhas.size()) {
            linhas.set(row, cpf + ";" + turma);
            java.nio.file.Files.write(file.toPath(), linhas);
        }
    }

    private void removerMatriculaArquivo(int row) {
        try {
            java.io.File file = new java.io.File("src/main/resources/data/matriculas.txt");
            java.util.List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
            if (row >= 0 && row < linhas.size()) {
                linhas.remove(row);
                java.nio.file.Files.write(file.toPath(), linhas);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir matrícula do arquivo: " + e.getMessage());
        }
    }
}
