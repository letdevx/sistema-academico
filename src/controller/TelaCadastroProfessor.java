package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.List;

public class TelaCadastroProfessor extends JPanel {

    private JTextField campoNome, campoCpf, campoEmail, campoTelefone;
    private JComboBox<String> comboCurso, comboDisciplina, filtroCurso;
    private DefaultTableModel tableModel;
    private JTable tabela;

    public TelaCadastroProfessor() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Painel de cadastro
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Professor"));
        formPanel.setBackground(new Color(240, 255, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        campoNome = new JTextField(20);
        campoCpf = new JTextField(15);
        campoEmail = new JTextField(20);
        campoTelefone = new JTextField(15);
        String[] cursos = {"Engenharia", "Direito", "Medicina", "Administração"};
        comboCurso = new JComboBox<>(cursos);
        comboDisciplina = new JComboBox<>(new String[]{"Matemática", "Biologia", "Direito Penal", "Estruturas"});

        formPanel.add(new JLabel("Nome completo:"), gbc);
        gbc.gridx = 1; formPanel.add(campoNome, gbc);
        gbc.gridx = 0; gbc.gridy++;

        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; formPanel.add(campoCpf, gbc);
        gbc.gridx = 0; gbc.gridy++;

        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; formPanel.add(campoEmail, gbc);
        gbc.gridx = 0; gbc.gridy++;

        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; formPanel.add(campoTelefone, gbc);
        gbc.gridx = 0; gbc.gridy++;

        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1; formPanel.add(comboCurso, gbc);
        gbc.gridx = 0; gbc.gridy++;

        formPanel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1; formPanel.add(comboDisciplina, gbc);
        gbc.gridx = 0; gbc.gridy++;

        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(this::salvarDados);
        formPanel.add(salvar, gbc);

        gbc.gridx = 1;
        JButton limpar = new JButton("Limpar");
        limpar.addActionListener(e -> limparCampos());
        formPanel.add(limpar, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Filtro
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.WHITE);
        filtroPanel.add(new JLabel("Filtrar por curso:"));
        filtroCurso = new JComboBox<>(cursos);
        filtroCurso.insertItemAt("Todos", 0);
        filtroCurso.setSelectedIndex(0);
        filtroCurso.addActionListener(e -> aplicarFiltro());
        filtroPanel.add(filtroCurso);

        JButton excluir = new JButton("Excluir selecionado");
        excluir.addActionListener(e -> excluirSelecionado());
        filtroPanel.add(excluir);

        add(filtroPanel, BorderLayout.SOUTH);

        // Tabela
        String[] colunas = {"Nome", "CPF", "E-mail", "Telefone", "Curso", "Disciplina"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        tabela.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        // Salvar alterações da tabela ao editar
        tableModel.addTableModelListener(e -> salvarTabelaNoArquivo());

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Professores Cadastrados"));
        add(scrollPane, BorderLayout.CENTER);

        carregarProfessores();
    }

    private void salvarDados(ActionEvent e) {
        String[] dados = {
            campoNome.getText(), campoCpf.getText(), campoEmail.getText(), campoTelefone.getText(),
            (String) comboCurso.getSelectedItem(), (String) comboDisciplina.getSelectedItem()
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("professores.txt", true))) {
            writer.write(String.join(";", dados));
            writer.newLine();
            tableModel.addRow(dados);
            JOptionPane.showMessageDialog(this, "Professor salvo com sucesso!");
            limparCampos();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar professor!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        comboCurso.setSelectedIndex(0);
        comboDisciplina.setSelectedIndex(0);
    }

    private void carregarProfessores() {
        try (BufferedReader reader = new BufferedReader(new FileReader("professores.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                tableModel.addRow(linha.split(";"));
            }
        } catch (IOException e) {
            // Ignora erro se o arquivo ainda não existir
        }
    }

    private void salvarTabelaNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("professores.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Vector<?> row = (Vector<?>) tableModel.getDataVector().elementAt(i);
                writer.write(row.stream().map(Object::toString).collect(Collectors.joining(";")));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int row = tabela.getSelectedRow();
        if (row >= 0) {
            tableModel.removeRow(row);
            salvarTabelaNoArquivo();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um professor para excluir.");
        }
    }

    private void aplicarFiltro() {
        String cursoSelecionado = (String) filtroCurso.getSelectedItem();
        tableModel.setRowCount(0); // limpa a tabela

        try (BufferedReader reader = new BufferedReader(new FileReader("professores.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if ("Todos".equals(cursoSelecionado) || dados[4].equals(cursoSelecionado)) {
                    tableModel.addRow(dados);
                }
            }
        } catch (IOException e) {
            // Ignora erro
        }
    }
}
