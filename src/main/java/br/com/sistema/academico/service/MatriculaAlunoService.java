package br.com.sistema.academico.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MatriculaAlunoService {
    private String arquivoMatriculas = "src/main/resources/data/matriculas.txt";

    public void setArquivoMatriculas(String arquivo) {
        this.arquivoMatriculas = arquivo;
    }

    public boolean validarCampos(String cpf, String turma) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione um aluno válido.");
        }
        if (turma == null || turma.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione uma turma válida.");
        }
        return true;
    }

    public void salvarMatricula(String cpf, String turma) throws IOException {
        File arquivo = new File(arquivoMatriculas);
        arquivo.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(cpf + ";" + turma);
            writer.newLine();
        }
    }
}
