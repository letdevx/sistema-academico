package controller;

import factory.ComponenteFactory;
import factory.SwingComponenteFactory;
import builder.AlunoBuilder;
import model.Aluno;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroAluno extends TelaCadastroTemplate {

    private static TelaCadastroAluno instancia; // Padrão Singleton

    public static TelaCadastroAluno getInstancia() {
        if (instancia == null) {
            instancia = new TelaCadastroAluno();
        }
        return instancia;
    }

    // Construtor privado (Singleton)
    private TelaCadastroAluno() {
        super("Cadastro de Aluno");
    }

    private JTextField campoNome, campoCpf, campoCurso, campoPeriodo;

    @Override
    protected JPanel criarFormulario() {
        ComponenteFactory factory = new SwingComponenteFactory();
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        campoNome = factory.criarCampoTexto();
        campoCpf = factory.criarCampoTexto();
        campoCurso = factory.criarCampoTexto();
        campoPeriodo = factory.criarCampoTexto();

        painel.add(factory.criarLabel("Nome:"));
        painel.add(campoNome);

        painel.add(factory.criarLabel("CPF:"));
        painel.add(campoCpf);

        painel.add(factory.criarLabel("Curso:"));
        painel.add(campoCurso);

        painel.add(factory.criarLabel("Período:"));
        painel.add(campoPeriodo);

        return painel;
    }

    @Override
    protected JPanel criarBotoes() {
        ComponenteFactory factory = new SwingComponenteFactory();
        JPanel painel = new JPanel();

        JButton btnSalvar = factory.criarBotao("Salvar");
        btnSalvar.addActionListener(e -> salvarAluno());

        painel.add(btnSalvar);
        return painel;
    }

    private void salvarAluno() {
        Aluno aluno = new AlunoBuilder()
                .setNome(campoNome.getText())
                .setCpf(campoCpf.getText())
                .setCurso(campoCurso.getText())
                .setPeriodo(campoPeriodo.getText())
                .build();

        try (FileWriter writer = new FileWriter("alunos.txt", true)) {
            writer.write(aluno.getNome() + ";" + aluno.getCpf() + ";" + aluno.getCurso() + ";" + aluno.getPeriodo() + "\n");
            JOptionPane.showMessageDialog(this, "Aluno salvo com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno.");
        }
    }
}
