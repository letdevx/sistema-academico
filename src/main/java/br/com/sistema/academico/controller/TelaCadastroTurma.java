package br.com.sistema.academico.controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.service.DisciplinaService;
import br.com.sistema.academico.service.TurmaService;

public class TelaCadastroTurma extends TelaCadastroTemplate {

    private JTextField campoNomeTurma;
    private JTextField campoCurso;
    private JTextField campoTurno;
    private JTextField campoAnoSemestre;
    private JList<String> listaDisciplinas;
    private final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nome da Turma", "Curso", "Turno", "Ano/Semestre", "Disciplinas"}, 0);

    private final TurmaService turmaService = new TurmaService();
    private DisciplinaService disciplinaService;

    public TelaCadastroTurma() {
        super("Cadastro de Turma");
        carregarTurmas();
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome da Turma:"), gbc);
        gbc.gridx = 1;
        formPanel.add(campoNomeTurma, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        formPanel.add(campoCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Turno:"), gbc);
        gbc.gridx = 1;
        formPanel.add(campoTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Ano/Semestre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(campoAnoSemestre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Disciplinas:"), gbc);
        gbc.gridx = 1;

        DefaultListModel<String> modeloDisciplinas = new DefaultListModel<>();
        try {
            List<String> disciplinas = disciplinaService.listarDisciplinas();
            if (disciplinas.isEmpty()) {
                modeloDisciplinas.addElement("Nenhuma disciplina disponível");
            } else {
                for (String d : disciplinas) {
                    modeloDisciplinas.addElement(d);
                }
            }
        } catch (IOException e) {
            modeloDisciplinas.addElement("Erro ao carregar disciplinas");
        }

        listaDisciplinas = new JList<>(modeloDisciplinas);
        listaDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroll = new JScrollPane(listaDisciplinas);
        scroll.setPreferredSize(new Dimension(200, 80));
        formPanel.add(scroll, gbc);

        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        formPanel.add(tableScroll, gbc);

        return formPanel;
    }

    @Override
    protected JPanel criarBotoes() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Salvar Turma");
        btnSalvar.addActionListener(e -> salvarTurma());
        bottomPanel.add(btnSalvar);
        return bottomPanel;
    }

    private void salvarTurma() {
        String nomeTurma = campoNomeTurma.getText().trim();
        String curso = campoCurso.getText().trim();
        String turno = campoTurno.getText().trim();
        String anoSemestre = campoAnoSemestre.getText().trim();
        List<String> disciplinasSelecionadas = listaDisciplinas.getSelectedValuesList();

        try {
            turmaService.validarCampos(nomeTurma, curso, turno, anoSemestre, disciplinasSelecionadas);
            turmaService.salvarTurma(nomeTurma, curso, turno, anoSemestre, disciplinasSelecionadas);
            tableModel.addRow(new Object[]{nomeTurma, curso, turno, anoSemestre, String.join(",", disciplinasSelecionadas)});
            limparCampos();
            mostrarMensagemSucesso("Turma salva com sucesso!");
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (IOException | RuntimeException e) {
            mostrarMensagemErro("Erro ao salvar turma: " + e.getMessage());
        }
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

    private void limparCampos() {
        campoNomeTurma.setText("");
        campoCurso.setText("");
        campoTurno.setText("");
        campoAnoSemestre.setText("");
        listaDisciplinas.clearSelection();
    }
}
