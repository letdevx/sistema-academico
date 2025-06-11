package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sistema.academico.service.LancamentoNotasService;

public class TelaLancamentoNotas extends JPanel {

    private final JComboBox<String> comboAlunos;
    private final JComboBox<String> comboDisciplinas;
    private final JTextField campoNota;
    private final JTextArea areaResultados;

    private final LancamentoNotasService lancamentoNotasService = new LancamentoNotasService();

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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/alunos.txt"))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/disciplinas.txt"))) {
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
        try {
            lancamentoNotasService.validarCampos(aluno, disciplina, nota);
            lancamentoNotasService.salvarNota(aluno, disciplina, nota);
            areaResultados.append("Nota registrada: " + aluno + " - " + disciplina + " - " + nota + "\n");
            campoNota.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (IOException | RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar nota: " + e.getMessage());
        }
    }

    private void carregarNotasExistentes() {
        areaResultados.setText("");
        java.io.File file = new java.io.File("notas.txt");
        if (!file.exists()) {
            areaResultados.setText("Nenhuma nota registrada ainda.");
            return;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                areaResultados.append("Registrado: " + linha + "\n");
            }
        } catch (IOException | RuntimeException e) {
            areaResultados.setText("Nenhuma nota registrada ainda.");
        }
    }
}

