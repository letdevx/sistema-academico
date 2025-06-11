package br.com.sistema.academico.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaService {
    private String arquivoDisciplinas = "src/main/resources/data/disciplinas.txt";

    public void setArquivoDisciplinas(String arquivo) {
        this.arquivoDisciplinas = arquivo;
    }

    public boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome da disciplina.");
        }
        return true;
    }

    public void salvarDisciplina(String nome) throws IOException {
        File arquivo = new File(arquivoDisciplinas);
        arquivo.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(nome);
            writer.newLine();
        }
    }

    public List<String> listarDisciplinas() throws IOException {
        List<String> disciplinas = new ArrayList<>();
        File arquivo = new File(arquivoDisciplinas);
        if (!arquivo.exists()) return disciplinas;
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                disciplinas.add(linha);
            }
        }
        return disciplinas;
    }
}
