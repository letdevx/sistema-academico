package br.com.sistema.academico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.sistema.academico.dao.ProfessorRepository;
import br.com.sistema.academico.model.Professor;

public class MockProfessorRepository extends ProfessorRepository {
    private final List<Professor> professores = new ArrayList<>();

    public MockProfessorRepository() {
        super(); // Pode ser vazio, pois não usaremos o banco real
    }

    @Override
    public void save(Professor professor) {
        professores.add(professor);
    }

    @Override
    public Optional<Professor> findByCpf(String cpf) {
        return professores.stream().filter(p -> p.getCpf().equals(cpf)).findFirst();
    }

    @Override
    public java.util.List<Professor> findAll() {
        return new ArrayList<>(professores);
    }

    @Override
    public void update(Professor professor) {}
    @Override
    public void delete(Professor professor) {}
    @Override
    public Optional<Professor> findById(Long id) { return Optional.empty(); }
}
