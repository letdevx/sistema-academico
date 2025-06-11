package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.sistema.academico.service.DisciplinaService;

public class TelaCadastroDisciplina extends JPanel {

    private JTextField campoNomeDisciplina;
    private DefaultListModel<String> listaModel;
    private JList<String> listaDisciplinas;

    private DisciplinaService disciplinaService = new DisciplinaService();

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
        try {
            disciplinaService.validarNome(nome);
            disciplinaService.salvarDisciplina(nome);
            listaModel.addElement(nome);
            campoNomeDisciplina.setText("");
            JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar disciplina: " + e.getMessage());
        }
    }

    private void carregarDisciplinas() {
        listaModel.clear();
        java.io.File file = new java.io.File("src/main/resources/data/disciplinas.txt");
        if (!file.exists())
            return;
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                listaModel.addElement(linha);
            }
        } catch (Exception e) {
            // Arquivo ainda não existe ou erro de leitura
        }
    }
}
