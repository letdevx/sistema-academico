package br.com.sistema.academico.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MatriculaDisciplinaService {
    private String arquivoMatriculasDisciplinas = "src/main/resources/data/matriculas_disciplinas.txt";

    public void setArquivoMatriculasDisciplinas(String arquivo) {
        this.arquivoMatriculasDisciplinas = arquivo;
    }

    public boolean validarCampos(String cpf, String turma, List<String> disciplinas) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione um aluno válido.");
        }
        if (turma == null || turma.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione uma turma válida.");
        }
        if (disciplinas == null || disciplinas.isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos uma disciplina.");
        }
        return true;
    }

    public void salvarMatricula(String cpf, String turma, List<String> disciplinas) throws IOException {
        File arquivo = new File(arquivoMatriculasDisciplinas);
        arquivo.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(cpf + ";" + turma + ";" + String.join(",", disciplinas));
            writer.newLine();
        }
    }
}
