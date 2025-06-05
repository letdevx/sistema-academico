package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroProfessor extends JPanel {

    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JComboBox<String> comboCurso;
    private JComboBox<String> comboDisciplina;

    public TelaCadastroProfessor() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Professor"));
        formPanel.setBackground(new Color(240, 255, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

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
        formPanel.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        formPanel.add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        campoTelefone = new JTextField(15);
        formPanel.add(campoTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        comboCurso = new JComboBox<>(new String[]{"Engenharia", "Direito", "Medicina", "Administração"});
        formPanel.add(comboCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Disciplina:"), gbc);
        gbc.gridx = 1;
        comboDisciplina = new JComboBox<>(new String[]{"Matemática", "Biologia", "Direito Penal", "Estruturas"});
        formPanel.add(comboDisciplina, gbc);

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("professores.txt", true))) {
            writer.write(
                campoNome.getText() + ";" +
                campoCpf.getText() + ";" +
                campoEmail.getText() + ";" +
                campoTelefone.getText() + ";" +
                comboCurso.getSelectedItem() + ";" +
                comboDisciplina.getSelectedItem()
            );
            writer.newLine();
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
}
