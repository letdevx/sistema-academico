package br.com.sistema.academico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.sistema.academico.dao.AlunoRepository;
import br.com.sistema.academico.model.Aluno;

public class MockAlunoRepository extends AlunoRepository {
    private final List<Aluno> alunos = new ArrayList<>();

    @Override
    public void save(Aluno aluno) {
        alunos.add(aluno);
    }

    @Override
    public Optional<Aluno> findByCpf(String cpf) {
        return alunos.stream().filter(a -> a.getCpf().equals(cpf)).findFirst();
    }

    @Override
    public List<Aluno> findAll() {
        return new ArrayList<>(alunos);
    }

    @Override
    public void update(Aluno aluno) {}
    @Override
    public void delete(Aluno aluno) {}
    @Override
    public Optional<Aluno> findById(Long id) { return Optional.empty(); }
}
