package br.com.sistema.academico.service;

import java.util.regex.Pattern;

import br.com.sistema.academico.dao.ProfessorRepository;
import br.com.sistema.academico.model.Professor;

public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public ProfessorService() {
        this.professorRepository = new ProfessorRepository();
    }

    // Construtor alternativo para testes, permitindo injetar um mock de ProfessorDAO
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public boolean validarCampos(String nome, String cpf, String departamento, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório!");
        }
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        if (cpfJaExiste(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório!");
        }
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido!");
        }
        return true;
    }

    public void salvarProfessor(String nome, String cpf, String departamento, String email) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setCpf(cpf);
        professor.setDepartamento(departamento);
        professor.setEmail(email);
        professorRepository.save(professor);
    }

    public java.util.List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    private boolean validarEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean validarCPF(String cpf) {
        return CPF_PATTERN.matcher(cpf).matches();
    }

    private boolean cpfJaExiste(String cpf) {
        return professorRepository.findByCpf(cpf).isPresent();
    }
}
