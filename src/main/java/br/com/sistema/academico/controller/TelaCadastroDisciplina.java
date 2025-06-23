package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.service.DisciplinaService;

public class TelaCadastroDisciplina extends JPanel {
    private final JTextField campoNomeDisciplina;
    private final DefaultTableModel tableModel;
    private final JTable tabelaDisciplinas;
    private int disciplinaEditando = -1;
    private final DisciplinaService disciplinaService = new DisciplinaService();

    public TelaCadastroDisciplina() {
        setLayout(new BorderLayout());
        JPanel painelFormulario = new JPanel(new GridLayout(1, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Cadastro de Disciplinas"));
        campoNomeDisciplina = new JTextField();
        painelFormulario.add(new JLabel("Nome da Disciplina:"));
        painelFormulario.add(campoNomeDisciplina);
        add(painelFormulario, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"Nome da Disciplina"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaDisciplinas = new JTable(tableModel);
        tabelaDisciplinas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDisciplinas.setRowHeight(28);
        tabelaDisciplinas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaDisciplinas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tabelaDisciplinas);
        add(scroll, BorderLayout.CENTER);
        carregarDisciplinas();

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Salvar");
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
        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarDisciplina());
        btnEditar.addActionListener(e -> editarDisciplina());
        btnExcluir.addActionListener(e -> excluirDisciplina());

        tabelaDisciplinas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabelaDisciplinas.getSelectedRow();
                if (row != -1) {
                    campoNomeDisciplina.setText((String) tableModel.getValueAt(row, 0));
                    disciplinaEditando = row;
                }
            }
        });
    }

    private void salvarDisciplina() {
        String nome = campoNomeDisciplina.getText().trim();
        try {
            disciplinaService.validarNome(nome);
            if (disciplinaEditando >= 0) {
                atualizarDisciplinaArquivo(disciplinaEditando, nome);
                tableModel.setValueAt(nome, disciplinaEditando, 0);
                JOptionPane.showMessageDialog(this, "Disciplina editada com sucesso!");
                disciplinaEditando = -1;
            } else {
                disciplinaService.salvarDisciplina(nome);
                tableModel.addRow(new Object[]{nome});
                JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso!");
            }
            campoNomeDisciplina.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar disciplina: " + e.getMessage());
        }
    }

    private void editarDisciplina() {
        int row = tabelaDisciplinas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para editar.");
            return;
        }
        campoNomeDisciplina.setText((String) tableModel.getValueAt(row, 0));
        disciplinaEditando = row;
    }

    private void excluirDisciplina() {
        int row = tabelaDisciplinas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina para excluir.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta disciplina?", "Confirmação", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        removerDisciplinaArquivo(row);
        tableModel.removeRow(row);
        campoNomeDisciplina.setText("");
        disciplinaEditando = -1;
        JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso!");
    }

    private void carregarDisciplinas() {
        tableModel.setRowCount(0);
        java.io.File file = new java.io.File("src/main/resources/data/disciplinas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                tableModel.addRow(new Object[]{linha});
            }
        } catch (IOException | RuntimeException e) {
            // Ignorar erro de leitura inicial
        }
    }

    private void atualizarDisciplinaArquivo(int row, String nome) throws IOException {
        java.io.File file = new java.io.File("src/main/resources/data/disciplinas.txt");
        java.util.List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
        if (row >= 0 && row < linhas.size()) {
            linhas.set(row, nome);
            java.nio.file.Files.write(file.toPath(), linhas);
        }
    }

    private void removerDisciplinaArquivo(int row) {
        try {
            java.io.File file = new java.io.File("src/main/resources/data/disciplinas.txt");
            java.util.List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
            if (row >= 0 && row < linhas.size()) {
                linhas.remove(row);
                java.nio.file.Files.write(file.toPath(), linhas);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir disciplina do arquivo: " + e.getMessage());
        }
    }
}
