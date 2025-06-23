package br.com.sistema.academico.controller;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.service.DisciplinaService;
import br.com.sistema.academico.service.TurmaService;

public class TelaCadastroTurma extends TelaCadastroTemplate {

    private JTextField campoNomeTurma;
    private JTextField campoCurso;
    private JTextField campoTurno;
    private JTextField campoAnoSemestre;
    private JComboBox<String> comboDisciplinas;
    private DefaultTableModel tableModel;
    private JTable tabelaTurmas;

    private final TurmaService turmaService = new TurmaService();
    private DisciplinaService disciplinaService;

    public TelaCadastroTurma() {
        super("Cadastro de Turma");
        // NÃO chamar carregarTurmas() aqui!
    }

    @Override
    protected JPanel criarFormulario() {
        disciplinaService = new DisciplinaService();

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Turma"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        campoNomeTurma = new JTextField(20);
        campoCurso = new JTextField(20);
        campoTurno = new JTextField(20);
        campoAnoSemestre = new JTextField(20);
        comboDisciplinas = new JComboBox<>();
        try {
            List<String> disciplinas = disciplinaService.listarDisciplinas();
            if (disciplinas.isEmpty()) {
                comboDisciplinas.addItem("Nenhuma disciplina disponível");
            } else {
                for (String d : disciplinas) {
                    comboDisciplinas.addItem(d);
                }
            }
        } catch (IOException e) {
            comboDisciplinas.addItem("Erro ao carregar disciplinas");
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nome da Turma:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(campoNomeTurma, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(campoCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Turno:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(campoTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Ano/Semestre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(campoAnoSemestre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Disciplinas:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(comboDisciplinas, gbc);

        // Inicializa o tableModel aqui
        tableModel = new DefaultTableModel(new Object[]{"Nome da Turma", "Curso", "Turno", "Ano/Semestre", "Disciplinas"}, 0);
        tabelaTurmas = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(tabelaTurmas);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(tableScroll, gbc);

        carregarTurmas(); // Agora sim, tableModel já está pronto

        return formPanel;
    }

    @Override
    protected JPanel criarBotoes() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Salvar Turma");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        // Bootstrap: Azul (Salvar), Amarelo (Editar), Vermelho (Excluir)
        btnSalvar.setBackground(new java.awt.Color(13, 110, 253)); // Azul
        btnSalvar.setForeground(java.awt.Color.BLACK); // Label preto
        btnSalvar.setFont(btnSalvar.getFont().deriveFont(java.awt.Font.BOLD));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 110, 253)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setOpaque(true);
        btnSalvar.setContentAreaFilled(true);

        btnEditar.setBackground(new java.awt.Color(255, 193, 7)); // Amarelo
        btnEditar.setForeground(java.awt.Color.BLACK); // Label preto
        btnEditar.setFont(btnEditar.getFont().deriveFont(java.awt.Font.BOLD));
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 193, 7)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setOpaque(true);
        btnEditar.setContentAreaFilled(true);

        btnExcluir.setBackground(new java.awt.Color(220, 53, 69)); // Vermelho
        btnExcluir.setForeground(java.awt.Color.BLACK); // Label preto
        btnExcluir.setFont(btnExcluir.getFont().deriveFont(java.awt.Font.BOLD));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 53, 69)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setOpaque(true);
        btnExcluir.setContentAreaFilled(true);

        btnSalvar.addActionListener(e -> salvarTurma());
        btnEditar.addActionListener(e -> editarTurma());
        btnExcluir.addActionListener(e -> excluirTurma());

        bottomPanel.add(btnSalvar);
        bottomPanel.add(btnEditar);
        bottomPanel.add(btnExcluir);
        return bottomPanel;
    }

    private int turmaEditando = -1;

    private void salvarTurma() {
        String nomeTurma = campoNomeTurma.getText().trim();
        String curso = campoCurso.getText().trim();
        String turno = campoTurno.getText().trim();
        String anoSemestre = campoAnoSemestre.getText().trim();
        String disciplinaSelecionada = (String) comboDisciplinas.getSelectedItem();

        try {
            turmaService.validarCampos(nomeTurma, curso, turno, anoSemestre, List.of(disciplinaSelecionada));
            if (turmaEditando >= 0) {
                // Atualizar turma existente
                atualizarTurmaArquivo(turmaEditando, nomeTurma, curso, turno, anoSemestre, disciplinaSelecionada);
                tableModel.setValueAt(nomeTurma, turmaEditando, 0);
                tableModel.setValueAt(curso, turmaEditando, 1);
                tableModel.setValueAt(turno, turmaEditando, 2);
                tableModel.setValueAt(anoSemestre, turmaEditando, 3);
                tableModel.setValueAt(disciplinaSelecionada, turmaEditando, 4);
                mostrarMensagemSucesso("Turma editada com sucesso!");
                turmaEditando = -1;
            } else {
                turmaService.salvarTurma(nomeTurma, curso, turno, anoSemestre, List.of(disciplinaSelecionada));
                tableModel.addRow(new Object[]{nomeTurma, curso, turno, anoSemestre, disciplinaSelecionada});
                mostrarMensagemSucesso("Turma salva com sucesso!");
            }
            limparCampos();
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (IOException | RuntimeException e) {
            mostrarMensagemErro("Erro ao salvar turma: " + e.getMessage());
        }
    }

    private void editarTurma() {
        int row = getTabelaSelecionada();
        if (row == -1) {
            mostrarMensagemErro("Selecione uma turma para editar.");
            return;
        }
        campoNomeTurma.setText((String) tableModel.getValueAt(row, 0));
        campoCurso.setText((String) tableModel.getValueAt(row, 1));
        campoTurno.setText((String) tableModel.getValueAt(row, 2));
        campoAnoSemestre.setText((String) tableModel.getValueAt(row, 3));
        comboDisciplinas.setSelectedItem(tableModel.getValueAt(row, 4));
        turmaEditando = row;
    }

    private void excluirTurma() {
        int row = getTabelaSelecionada();
        if (row == -1) {
            mostrarMensagemErro("Selecione uma turma para excluir.");
            return;
        }
        if (!confirmarAcao("Deseja realmente excluir esta turma?")) return;
        removerTurmaArquivo(row);
        tableModel.removeRow(row);
        limparCampos();
        mostrarMensagemSucesso("Turma excluída com sucesso!");
        turmaEditando = -1;
    }

    private void carregarTurmas() {
        tableModel.setRowCount(0);
        java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
        if (!file.exists()) return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    tableModel.addRow(new Object[]{dados[0], dados[1], dados[2], dados[3], dados[4]});
                }
            }
        } catch (IOException | RuntimeException e) {
            // Ignorar erro de leitura inicial
        }
    }

    private int getTabelaSelecionada() {
        return tabelaTurmas.getSelectedRow();
    }

    private void atualizarTurmaArquivo(int row, String nome, String curso, String turno, String anoSemestre, String disciplina) throws IOException {
        java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
        List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
        if (row >= 0 && row < linhas.size()) {
            linhas.set(row, nome + ";" + curso + ";" + turno + ";" + anoSemestre + ";" + disciplina);
            java.nio.file.Files.write(file.toPath(), linhas);
        }
    }

    private void removerTurmaArquivo(int row) {
        try {
            java.io.File file = new java.io.File("src/main/resources/data/turmas.txt");
            List<String> linhas = java.nio.file.Files.readAllLines(file.toPath());
            if (row >= 0 && row < linhas.size()) {
                linhas.remove(row);
                java.nio.file.Files.write(file.toPath(), linhas);
            }
        } catch (IOException e) {
            mostrarMensagemErro("Erro ao excluir turma do arquivo: " + e.getMessage());
        }
    }

    private void limparCampos() {
        campoNomeTurma.setText("");
        campoCurso.setText("");
        campoTurno.setText("");
        campoAnoSemestre.setText("");
        comboDisciplinas.setSelectedIndex(0);
    }
}
