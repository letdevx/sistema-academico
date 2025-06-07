package controller;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class TelaLancamentoNotas extends JPanel {

    private JComboBox<String> comboAlunos;
    private JComboBox<String> comboDisciplinas;
    private JTextField campoNota;
    private JTextArea areaResultados;

    public TelaLancamentoNotas() {
        setLayout(new BorderLayout());

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Lançamento de Notas"));

        comboAlunos = new JComboBox<>();
        carregarAlunos();

        comboDisciplinas = new JComboBox<>();
        carregarDisciplinas();

        campoNota = new JTextField();

        JButton btnSalvar = new JButton("Salvar Nota");
        btnSalvar.addActionListener(e -> salvarNota());

        painelFormulario.add(new JLabel("Aluno:"));
        painelFormulario.add(comboAlunos);
        painelFormulario.add(new JLabel("Disciplina:"));
        painelFormulario.add(comboDisciplinas);
        painelFormulario.add(new JLabel("Nota:"));
        painelFormulario.add(campoNota);
        painelFormulario.add(new JLabel(""));
        painelFormulario.add(btnSalvar);

        areaResultados = new JTextArea(10, 50);
        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);

        add(painelFormulario, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        carregarNotasExistentes();
    }

    private void carregarAlunos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("alunos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length > 0) {
                    comboAlunos.addItem(dados[0]); // Nome do aluno
                }
            }
        } catch (IOException e) {
            comboAlunos.addItem("Nenhum aluno encontrado");
        }
    }

    private void carregarDisciplinas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("disciplinas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                comboDisciplinas.addItem(linha);
            }
        } catch (IOException e) {
            comboDisciplinas.addItem("Nenhuma disciplina encontrada");
        }
    }

    private void salvarNota() {
        String aluno = (String) comboAlunos.getSelectedItem();
        String disciplina = (String) comboDisciplinas.getSelectedItem();
        String nota = campoNota.getText().trim();

        if (aluno == null || disciplina == null || nota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("notas.txt", true))) {
            writer.write(aluno + ";" + disciplina + ";" + nota);
            writer.newLine();
            areaResultados.append("Nota registrada: " + aluno + " - " + disciplina + " - " + nota + "\n");
            campoNota.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar nota.");
        }
    }

    private void carregarNotasExistentes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("notas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                areaResultados.append("Registrado: " + linha + "\n");
            }
        } catch (IOException e) {
            areaResultados.setText("Nenhuma nota registrada ainda.");
        }
    }
}

