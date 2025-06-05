package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroAluno extends JPanel {

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoNascimento;
    private JComboBox<String> comboSexo;

    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JTextField campoEndereco;

    private JComboBox<String> comboCurso;
    private JComboBox<String> comboGrau;
    private JComboBox<String> comboTurno;
    private JTextField campoIngresso;
    private JComboBox<String> comboSituacao;

    public TelaCadastroAluno() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Aluno - Universidade"));
        formPanel.setBackground(new Color(245, 255, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Campos
        formPanel.add(new JLabel("Nome completo:"), gbc);
        gbc.gridx = 1;
        campoNome = new JTextField(20);
        formPanel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        campoCpf = new JTextField(15);
        formPanel.add(campoCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Data de nascimento:"), gbc);
        gbc.gridx = 1;
        campoNascimento = new JTextField(10);
        formPanel.add(campoNascimento, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        comboSexo = new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro", "Prefere não dizer"});
        formPanel.add(comboSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("E-mail pessoal:"), gbc);
        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        formPanel.add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Telefone celular:"), gbc);
        gbc.gridx = 1;
        campoTelefone = new JTextField(15);
        formPanel.add(campoTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Endereço completo:"), gbc);
        gbc.gridx = 1;
        campoEndereco = new JTextField(30);
        formPanel.add(campoEndereco, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        comboCurso = new JComboBox<>(new String[]{"Engenharia", "Direito", "Medicina", "Administração"});
        formPanel.add(comboCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Grau:"), gbc);
        gbc.gridx = 1;
        comboGrau = new JComboBox<>(new String[]{"Bacharelado", "Licenciatura", "Tecnólogo"});
        formPanel.add(comboGrau, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Turno:"), gbc);
        gbc.gridx = 1;
        comboTurno = new JComboBox<>(new String[]{"Matutino", "Vespertino", "Noturno"});
        formPanel.add(comboTurno, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Data de ingresso:"), gbc);
        gbc.gridx = 1;
        campoIngresso = new JTextField(10);
        formPanel.add(campoIngresso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Situação:"), gbc);
        gbc.gridx = 1;
        comboSituacao = new JComboBox<>(new String[]{"Ativo", "Trancado", "Desligado", "Formado"});
        formPanel.add(comboSituacao, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(this::salvarDados);
        formPanel.add(salvar, gbc);

        gbc.gridx = 1;
        JButton limpar = new JButton("Limpar");
        limpar.addActionListener(e -> limparCampos());
        formPanel.add(limpar, gbc);

        add(formPanel, BorderLayout.NORTH);
    }

    private void salvarDados(ActionEvent e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alunos.txt", true))) {
            writer.write(
                campoNome.getText() + ";" +
                campoCpf.getText() + ";" +
                campoNascimento.getText() + ";" +
                comboSexo.getSelectedItem() + ";" +
                campoEmail.getText() + ";" +
                campoTelefone.getText() + ";" +
                campoEndereco.getText() + ";" +
                comboCurso.getSelectedItem() + ";" +
                comboGrau.getSelectedItem() + ";" +
                comboTurno.getSelectedItem() + ";" +
                campoIngresso.getText() + ";" +
                comboSituacao.getSelectedItem()
            );
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
            limparCampos();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoNascimento.setText("");
        comboSexo.setSelectedIndex(0);
        campoEmail.setText("");
        campoTelefone.setText("");
        campoEndereco.setText("");
        comboCurso.setSelectedIndex(0);
        comboGrau.setSelectedIndex(0);
        comboTurno.setSelectedIndex(0);
        campoIngresso.setText("");
        comboSituacao.setSelectedIndex(0);
    }
}
