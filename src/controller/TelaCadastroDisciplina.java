package controller;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TelaCadastroDisciplina extends JPanel {

    private JTextField campoNomeDisciplina;
    private DefaultListModel<String> listaModel;
    private JList<String> listaDisciplinas;

    public TelaCadastroDisciplina() {
        setLayout(new BorderLayout());

        JPanel painelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Cadastro de Disciplinas"));

        campoNomeDisciplina = new JTextField();
        painelFormulario.add(new JLabel("Nome da Disciplina:"));
        painelFormulario.add(campoNomeDisciplina);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarDisciplina());
        painelFormulario.add(btnSalvar);

        listaModel = new DefaultListModel<>();
        listaDisciplinas = new JList<>(listaModel);
        carregarDisciplinas();

        add(painelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(listaDisciplinas), BorderLayout.CENTER);
    }

    private void salvarDisciplina() {
        String nome = campoNomeDisciplina.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da disciplina.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("disciplinas.txt", true))) {
            writer.write(nome);
            writer.newLine();
            listaModel.addElement(nome);
            campoNomeDisciplina.setText("");
            JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar disciplina.");
        }
    }

    private void carregarDisciplinas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("disciplinas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                listaModel.addElement(linha);
            }
        } catch (IOException e) {
            // Arquivo ainda não existe
        }
    }
}
