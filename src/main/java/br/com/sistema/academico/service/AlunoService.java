package br.com.sistema.academico.service;

import java.util.List;

import br.com.sistema.academico.dao.AlunoRepository;
import br.com.sistema.academico.model.Aluno;

public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService() {
        this.alunoRepository = new AlunoRepository();
    }

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
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
        if (cpfJaExiste(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        return true;
    }

    public void salvarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public boolean cpfJaExiste(String cpf) {
        return alunoRepository.findByCpf(cpf).isPresent();
    }
}
