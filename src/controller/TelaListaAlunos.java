package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class TelaListaAlunos extends JPanel {
    private static final String CPF = null;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaListaAlunos() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        modelo = new DefaultTableModel(new String[]{"Nome", "CPF", "Nascimento", "Matrícula"}, 0);

        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        carregarDados();
    }

    private void carregarDados() {
        modelo.setRowCount(0); // limpa a tabela antes de carregar

        File arquivo = new File("alunos.txt");
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    modelo.addRow(dados);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
