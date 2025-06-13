package br.com.sistema.academico.controller;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.model.Aluno;
import br.com.sistema.academico.service.AlunoService;

public class TelaListaAlunos extends JPanel {

    public TelaListaAlunos() {
        setLayout(new BorderLayout());

        String[] colunas = {
            "Nome", "CPF", "Nascimento", "Sexo",
            "E-mail", "Telefone", "Endereço",
            "Curso", "Grau", "Turno", "Ingresso", "Situação", "Periodo"
        };

        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        AlunoService alunoService = new AlunoService();
        for (Aluno aluno : alunoService.listarAlunos()) {
            model.addRow(new Object[] {
                aluno.getNome(), aluno.getCpf(), aluno.getDataNascimento(), aluno.getSexo(),
                aluno.getEmail(), aluno.getTelefone(), aluno.getEndereco(),
                aluno.getCurso(), aluno.getGrau(), aluno.getTurno(),
                aluno.getDataIngresso(), aluno.getSituacao(), aluno.getPeriodo()
            });
        }
        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }
}
