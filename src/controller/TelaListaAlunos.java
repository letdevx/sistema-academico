package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Vector;

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
        try (BufferedReader reader = new BufferedReader(new FileReader("alunos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Vector<String> row = new Vector<>();
                for (String dado : dados) {
                    row.add(dado);
                }
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
