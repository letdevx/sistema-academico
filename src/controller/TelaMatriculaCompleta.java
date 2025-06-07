
package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class TelaMatriculaCompleta extends JPanel {

    private JComboBox<String> comboAlunos;
    private JComboBox<String> comboTurmas;
    private JList<String> listaDisciplinas;
    private DefaultTableModel tableModel;

    public TelaMatriculaCompleta() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Matrícula Completa (Turma + Disciplinas)"));
        formPanel.setBackground(new Color(240, 255, 250));

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
        try (BufferedReader reader = new BufferedReader(new FileReader("alunos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length > 1) {
                    comboAlunos.addItem(dados[0] + " - " + dados[1]); // Nome - CPF
                }
            }
        } catch (IOException e) {
            comboAlunos.addItem("Erro ao carregar alunos");
        }
    }

    private void carregarTurmas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("turmas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 1) {
                    comboTurmas.addItem(dados[0]);
                }
            }
        } catch (IOException e) {
            comboTurmas.addItem("Erro ao carregar turmas");
        }
    }

    private void atualizarDisciplinas() {
        String turmaSelecionada = (String) comboTurmas.getSelectedItem();
        if (turmaSelecionada == null) return;

        try (BufferedReader reader = new BufferedReader(new FileReader("turmas.txt"))) {
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

        boolean jaMatriculado = false;
        File arquivoTurma = new File("matriculas.txt");
        if (arquivoTurma.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoTurma))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (dados.length == 2 && dados[0].equals(cpf) && dados[1].equals(turma)) {
                        jaMatriculado = true;
                        break;
                    }
                }
            } catch (IOException ignored) {}
        }

        if (!jaMatriculado) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("matriculas.txt", true))) {
                writer.write(cpf + ";" + turma);
                writer.newLine();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula na turma.");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("matriculas_disciplinas.txt", true))) {
            writer.write(cpf + ";" + turma + ";" + disciplinasString);
            writer.newLine();
            tableModel.addRow(new String[]{cpf, turma, disciplinasString});
            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar matrícula nas disciplinas.");
        }
    }

    private void limparCampos() {
        comboAlunos.setSelectedIndex(0);
        comboTurmas.setSelectedIndex(0);
        listaDisciplinas.clearSelection();
    }

    private void carregarMatriculas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("matriculas_disciplinas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    tableModel.addRow(dados);
                }
            }
        } catch (IOException e) {
            // tudo bem se não existir
        }
    }
}
