package controller;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class TelaBoletimAluno extends JPanel {

    private JComboBox<String> comboAlunos;
    private JTextArea areaBoletim;

    public TelaBoletimAluno() {
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout());
        painelTopo.setBorder(BorderFactory.createTitledBorder("Boletim do Aluno"));

        comboAlunos = new JComboBox<>();
        carregarAlunos();

        JButton btnVerBoletim = new JButton("Ver Boletim");
        btnVerBoletim.addActionListener(e -> mostrarBoletim());

        painelTopo.add(new JLabel("Selecione o Aluno:"));
        painelTopo.add(comboAlunos);
        painelTopo.add(btnVerBoletim);

        areaBoletim = new JTextArea(15, 50);
        areaBoletim.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaBoletim);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarAlunos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("alunos.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length > 0) {
                    comboAlunos.addItem(dados[0]);
                }
            }
        } catch (IOException e) {
            comboAlunos.addItem("Nenhum aluno encontrado");
        }
    }

    private void mostrarBoletim() {
        String alunoSelecionado = (String) comboAlunos.getSelectedItem();
        if (alunoSelecionado == null || alunoSelecionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno válido.");
            return;
        }

        areaBoletim.setText("");
        Map<String, List<Double>> notasPorDisciplina = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("notas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 3 && dados[0].equals(alunoSelecionado)) {
                    String disciplina = dados[1];
                    double nota = Double.parseDouble(dados[2]);
                    notasPorDisciplina.putIfAbsent(disciplina, new ArrayList<>());
                    notasPorDisciplina.get(disciplina).add(nota);
                }
            }

            if (notasPorDisciplina.isEmpty()) {
                areaBoletim.setText("Nenhuma nota encontrada para " + alunoSelecionado + ".");
            } else {
                areaBoletim.append("Boletim de " + alunoSelecionado + ":\n\n");
                for (Map.Entry<String, List<Double>> entry : notasPorDisciplina.entrySet()) {
                    String disciplina = entry.getKey();
                    List<Double> notas = entry.getValue();
                    double media = notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                    areaBoletim.append("Disciplina: " + disciplina + "\n");
                    areaBoletim.append("Notas: " + notas + "\n");
                    areaBoletim.append("Média: " + String.format("%.2f", media) + "\n\n");
                }
            }

        } catch (IOException e) {
            areaBoletim.setText("Erro ao ler arquivo de notas.");
        }
    }
}
