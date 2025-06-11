package br.com.sistema.academico.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TurmaService {
    private String arquivoTurmas = "src/main/resources/data/turmas.txt";

    public void setArquivoTurmas(String arquivo) {
        this.arquivoTurmas = arquivo;
    }

    public boolean validarCampos(String nomeTurma, String curso, String turno, String anoSemestre, List<String> disciplinas) {
        if (nomeTurma == null || nomeTurma.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da turma é obrigatório!");
        }
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("Curso é obrigatório!");
        }
        if (turno == null || turno.trim().isEmpty()) {
            throw new IllegalArgumentException("Turno é obrigatório!");
        }
        if (anoSemestre == null || anoSemestre.trim().isEmpty()) {
            throw new IllegalArgumentException("Ano/Semestre é obrigatório!");
        }
        if (disciplinas == null || disciplinas.isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos uma disciplina!");
        }
        return true;
    }

    public void salvarTurma(String nomeTurma, String curso, String turno, String anoSemestre, List<String> disciplinas) throws IOException {
        File arquivo = new File(arquivoTurmas);
        arquivo.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(nomeTurma + ";" + curso + ";" + turno + ";" + anoSemestre + ";" + String.join(",", disciplinas));
            writer.newLine();
        }
    }
}
