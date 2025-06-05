package controller;

import model.Aluno;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TelaCadastroAluno extends JPanel {
    private JTextField nomeField, cpfField, dataNascField, matriculaField;
    private ArrayList<Aluno> listaAlunos = new ArrayList<>();

    public TelaCadastroAluno() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(new Color(240, 248, 255));

        nomeField = criarCampo("Nome:", formPanel);
        cpfField = criarCampo("CPF:", formPanel);
        dataNascField = criarCampo("Data de Nascimento:", formPanel);
        matriculaField = criarCampo("Matrícula:", formPanel);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvarButton = new JButton("Salvar");
        JButton limparButton = new JButton("Limpar");
        botoesPanel.add(salvarButton);
        botoesPanel.add(limparButton);
        botoesPanel.setOpaque(false);

        salvarButton.addActionListener(e -> salvarAluno());
        limparButton.addActionListener(e -> limparCampos());

        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(botoesPanel);

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topo.add(formPanel);
        topo.setOpaque(false);

        add(topo, BorderLayout.NORTH);
    }

    private JTextField criarCampo(String label, JPanel container) {
        JPanel linha = new JPanel();
        linha.setLayout(new BoxLayout(linha, BoxLayout.Y_AXIS));
        linha.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        linha.setBackground(new Color(240, 248, 255));

        JLabel lbl = new JLabel(label);
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        linha.add(lbl);
        linha.add(campo);
        container.add(linha);

        return campo;
    }

    private void salvarAluno() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String nascimento = dataNascField.getText().trim();
        String matricula = matriculaField.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || nascimento.isEmpty() || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Aluno aluno = new Aluno(nome, cpf, nascimento, matricula);
        listaAlunos.add(aluno);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alunos.txt", true))) {
            writer.write(nome + ";" + cpf + ";" + nascimento + ";" + matricula);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        dataNascField.setText("");
        matriculaField.setText("");
    }
}
