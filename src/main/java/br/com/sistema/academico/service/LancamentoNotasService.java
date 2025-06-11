package br.com.sistema.academico.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LancamentoNotasService {
    private String arquivoNotas = "notas.txt";

    public void setArquivoNotas(String arquivo) {
        this.arquivoNotas = arquivo;
    }

    public boolean validarCampos(String aluno, String disciplina, String nota) {
        if (aluno == null || aluno.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione um aluno válido.");
        }
        if (disciplina == null || disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("Selecione uma disciplina válida.");
        }
        if (nota == null || nota.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a nota.");
        }
        return true;
    }

    public void salvarNota(String aluno, String disciplina, String nota) throws IOException {
        File arquivo = new File(arquivoNotas);
        arquivo.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(aluno + ";" + disciplina + ";" + nota);
            writer.newLine();
        }
    }
}
