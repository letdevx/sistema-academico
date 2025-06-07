package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class TelaCadastroTurma extends JPanel {

    private JTextField campoNomeTurma;
    private JTextField campoCurso;
    private JTextField campoTurno;
    private JTextField campoAnoSemestre;
    private JList<String> listaDisciplinas;
    private DefaultTableModel tableModel;

    public TelaCadastroTurma() {
        setLayout(new BorderLayout());

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
        try (BufferedReader br = new BufferedReader(new FileReader("disciplinas.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                modeloDisciplinas.addElement(linha);
            }
        } catch (IOException e) {
            modeloDisciplinas.addElement("Nenhuma disciplina disponível");
        }

        listaDisciplinas = new JList<>(modeloDisciplinas);
        listaDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroll = new JScrollPane(listaDisciplinas);
        scroll.setPreferredSize(new Dimension(200, 80));
        formPanel.add(scroll, gbc);

        JButton btnSalvar = new JButton("Salvar Turma");
        btnSalvar.addActionListener(e -> salvarTurma());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnSalvar);

        tableModel = new DefaultTableModel(new Object[]{"Nome da Turma", "Curso", "Turno", "Ano/Semestre", "Disciplinas"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        carregarTurmas();

        add(formPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        add(tableScroll, BorderLayout.SOUTH);
    }

    private void salvarTurma() {
        String nomeTurma = campoNomeTurma.getText().trim();
        String curso = campoCurso.getText().trim();
        String turno = campoTurno.getText().trim();
        String anoSemestre = campoAnoSemestre.getText().trim();
        List<String> disciplinasSelecionadas = listaDisciplinas.getSelectedValuesList();

        if (nomeTurma.isEmpty() || curso.isEmpty() || turno.isEmpty() || anoSemestre.isEmpty() || disciplinasSelecionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("turmas.txt", true))) {
            writer.write(nomeTurma + ";" + curso + ";" + turno + ";" + anoSemestre + ";" + String.join(",", disciplinasSelecionadas));
            writer.newLine();
            tableModel.addRow(new Object[]{nomeTurma, curso, turno, anoSemestre, String.join(",", disciplinasSelecionadas)});
            limparCampos();
            JOptionPane.showMessageDialog(this, "Turma salva com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar turma.");
        }
    }

    private void carregarTurmas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("turmas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    tableModel.addRow(new Object[]{dados[0], dados[1], dados[2], dados[3], dados[4]});
                }
            }
        } catch (IOException e) {
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
