package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaListaAlunos extends JPanel {

    public TelaListaAlunos() {
        setLayout(new BorderLayout());

        String[] colunas = {
            "Nome", "CPF", "Nascimento", "Sexo",
            "E-mail", "Telefone", "Endereço",
            "Curso", "Grau", "Turno", "Ingresso", "Situação"
        };

        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        // Ler arquivo e preencher tabela
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/alunos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Vector<String> row = new Vector<>();
                row.addAll(Arrays.asList(dados));
                model.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler alunos.txt", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }
}
