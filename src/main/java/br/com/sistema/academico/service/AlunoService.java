package br.com.sistema.academico.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AlunoService {
    private String arquivoSaida = "src/main/resources/data/alunos.txt";

    public void setArquivoSaida(String arquivo) {
        this.arquivoSaida = arquivo;
    }

    public boolean validarCampos(String nome, String dataNascimento, int generoIndex, String cpf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (dataNascimento == null || dataNascimento.equals("__/__/____")) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória!");
        }
        if (generoIndex == 0) {
            throw new IllegalArgumentException("Selecione um gênero!");
        }
        if (cpf == null || cpf.equals("___.___.___-__")) {
            throw new IllegalArgumentException("CPF é obrigatório!");
        }
        return true;
    }

    public void salvarAluno(String nome, String dataNascimento, String genero, String cpf, String rg, String orgaoEmissor, String dataEmissao, String nacionalidade, String naturalidade, String estadoCivil) throws IOException {
        File arquivo = new File(arquivoSaida);
        arquivo.createNewFile();
        try (FileWriter writer = new FileWriter(arquivo, true)) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                nome,
                dataNascimento,
                genero,
                cpf,
                rg,
                orgaoEmissor,
                dataEmissao,
                nacionalidade,
                naturalidade,
                estadoCivil
            ));
        }
    }
}
